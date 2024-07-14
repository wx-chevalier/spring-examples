/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ylzl.eden.dynamic.cache.integration.l2cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.ylzl.eden.commons.lang.MessageFormatUtils;
import org.ylzl.eden.dynamic.cache.CacheType;
import org.ylzl.eden.dynamic.cache.L2Cache;
import org.ylzl.eden.dynamic.cache.config.CacheConfig;
import org.ylzl.eden.dynamic.cache.exception.TryLockFailedException;
import org.ylzl.eden.dynamic.cache.exception.ValueRetrievalException;
import org.ylzl.eden.dynamic.cache.support.AbstractAdaptingCache;
import org.ylzl.eden.dynamic.cache.support.value.NullValue;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Slf4j
public class RedisCache extends AbstractAdaptingCache implements L2Cache {

	private static final String CACHED_VALUE_IS_NOT_REQUIRED_TYPE = "Redis Cached value '{}' is not of required type '{}'";

	private final CacheConfig.L2Cache.Redis redisConfig;

	private final RedissonClient redisClient;

	private final RMap<Object, Object> rMap;

	public RedisCache(String cacheName, CacheConfig cacheConfig, RedissonClient redisClient) {
		super(cacheName, cacheConfig);
		this.redisConfig = cacheConfig.getL2Cache().getRedis();
		this.redisClient = redisClient;
		this.rMap = redisClient.getMap(cacheName);
	}

	/**
	 * 获取缓存类型
	 *
	 * @return 缓存类型
	 */
	@Override
	public String getCacheType() {
		return CacheType.REDIS.name();
	}

	/**
	 * 使用本地缓存提供类
	 * <p>针对定制化需求暴露底层的客户端实例操作</p>
	 *
	 * @return 本地缓存提供类
	 */
	@Override
	public RedissonClient getNativeCache() {
		return this.redisClient;
	}

	/**
	 * 获取指定key的缓存项
	 *
	 * @param key 缓存Key
	 * @return 缓存项
	 */
	@Override
	public Object get(Object key) {
		Object value = this.getBucket(key).get();
		return fromStoreValue(value);
	}

	/**
	 * 获取指定key的缓存项
	 *
	 * @param key  缓存Key
	 * @param type 缓存类型
	 * @return 缓存项
	 */
	@Override
	public <T> T get(Object key, Class<T> type) {
		Object value = this.get(key);
		if (value == null) {
			return null;
		}
		if (type != null && !type.isInstance(value)) {
			throw new IllegalStateException(MessageFormatUtils.format(CACHED_VALUE_IS_NOT_REQUIRED_TYPE, value, type.getName()));
		}
		return (T) value;
	}

	/**
	 * 获取指定key的缓存项，如果缓存项不存在，则通过 {@code valueLoader} 获取值
	 *
	 * @param key         缓存Key
	 * @param valueLoader 缓存加载器
	 * @return 缓存项
	 */
	@Override
	public <T> T get(Object key, @NotNull Callable<T> valueLoader) {
		RBucket<Object> bucket = this.getBucket(key);
		Object value = bucket.get();
		if (value != null) {
			return (T) fromStoreValue(value);
		}

		RLock lock = rMap.getLock(key);
		if (this.redisConfig.isTryLock()) {
			if (!lock.tryLock()) {
				throw new TryLockFailedException(MessageFormatUtils.format("Try lock fail, key = '{}'", key));
			}
		} else {
			lock.lock();
		}
		try {
			value = bucket.get();
			if (value == null) {
				value = valueLoader.call();
			}
			this.put(key, value);
		} catch (Exception e) {
			throw new ValueRetrievalException(MessageFormatUtils.format("Loading value fail, key = '{}'", key), valueLoader, e);
		} finally {
			lock.unlock();
		}
		return (T) fromStoreValue(value);
	}

	/**
	 * 设置指定key的缓存项
	 *
	 * @param key   缓存Key
	 * @param value 缓存项
	 */
	@Override
	public void put(Object key, Object value) {
		RBucket<Object> bucket = this.getBucket(key);
		if (!isAllowNullValues() && value == null) {
			bucket.delete();
		}

		Object storeValue = toStoreValue(value);
		long timeToLive = this.timeToLive(storeValue);
		if (timeToLive > 0) {
			bucket.trySet(storeValue, timeToLive, TimeUnit.SECONDS);
		} else {
			bucket.trySet(storeValue);
		}
	}

	/**
	 * 设置指定key的缓存项，如果已存在则忽略
	 *
	 * @param key   缓存Key
	 * @param value 缓存项
	 * @return 缓存项
	 */
	@Override
	public Object putIfAbsent(Object key, Object value) {
		if (!isAllowNullValues() && value == null) {
			return this.get(key);
		}

		RBucket<Object> bucket = this.getBucket(key);
		long timeToLive = this.timeToLive(value);
		if (timeToLive > 0) {
			bucket.trySet(value, timeToLive, TimeUnit.SECONDS);
		} else {
			bucket.trySet(value);
		}
		return fromStoreValue(bucket.get());
	}

	/**
	 * 删除指定的缓存项
	 *
	 * @param key 缓存Key
	 */
	@Override
	public void evict(Object key) {
		this.getBucket(key).delete();
	}

	/**
	 * 清空缓存
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException("Redis is not allowed flush db");
	}

	/**
	 * 判断Key是否存在
	 *
	 * @param key Key
	 * @return 是否存在
	 */
	@Override
	public boolean isExists(Object key) {
		return this.getBucket(key).isExists();
	}

	/**
	 * 获取Bucket实例
	 *
	 * @param key Key
	 * @return Bucket实例
	 */
	private RBucket<Object> getBucket(Object key) {
		return redisClient.getBucket(buildKey(key));
	}

	/**
	 * 获取缓存值的存活时间
	 *
	 * @param value 缓存值
	 * @return 缓存值的存活时间
	 */
	private long timeToLive(Object value) {
		long timeToLive = value instanceof NullValue ?
			this.getCacheConfig().getNullValueTimeToLive() :
			this.redisConfig.getTimeToLive();
		if (timeToLive < 0) {
			timeToLive = 0;
		}
		return timeToLive;
	}
}
