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

package org.ylzl.eden.dynamic.cache.integration.l1cache.guava;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.google.common.cache.Cache;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.ylzl.eden.commons.lang.MessageFormatUtils;
import org.ylzl.eden.dynamic.cache.CacheType;
import org.ylzl.eden.dynamic.cache.L1Cache;
import org.ylzl.eden.dynamic.cache.config.CacheConfig;
import org.ylzl.eden.dynamic.cache.support.AbstractAdaptingCache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Guava 缓存
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Slf4j
public class GuavaCache extends AbstractAdaptingCache implements L1Cache {

	private static final String CACHED_VALUE_IS_NOT_REQUIRED_TYPE = "Guava Cached value '{}' is not of required type '{}'";

	private final Cache<Object, Object> cache;

	public GuavaCache(String cacheName, CacheConfig cacheConfig, Cache<Object, Object> cache) {
		super(cacheName, cacheConfig);
		this.cache = cache;
	}

	/**
	 * 获取缓存类型
	 *
	 * @return 缓存类型
	 */
	@Override
	public String getCacheType() {
		return CacheType.GUAVA.name();
	}

	/**
	 * 使用本地缓存提供类
	 * <p>针对定制化需求暴露底层的客户端实例操作</p>
	 *
	 * @return 本地缓存提供类
	 */
	@Override
	public Cache<?, ?> getNativeCache() {
		return this.cache;
	}

	/**
	 * 获取指定key的缓存项
	 *
	 * @param key 缓存Key
	 * @return 缓存项
	 */
	@Override
	public Object get(Object key) {
		if (isLoadingCache()) {
			Object value = null;
			try {
				value = ((LoadingCache) this.cache).get(key);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
			return fromStoreValue(value);
		}
		return fromStoreValue(this.cache.getIfPresent(key));
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
		Object value = get(key);
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
	public <T> T get(Object key, Callable<T> valueLoader) {
		try {
			return (T) fromStoreValue(this.cache.get(key, valueLoader));
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置指定key的缓存项
	 *
	 * @param key   缓存Key
	 * @param value 缓存项
	 */
	@Override
	public void put(Object key, Object value) {
		this.cache.put(key, toStoreValue(value));
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
		Object cacheValue = this.cache.getIfPresent(key);
		if (cacheValue != null) {
			return cacheValue;
		}
		put(key, value);
		return value;
	}

	/**
	 * 删除指定的缓存项
	 *
	 * @param key 缓存Key
	 */
	@Override
	public void evict(Object key) {
		this.cache.invalidate(key);
	}

	/**
	 * 删除指定的缓存项，如果不存在则返回 false
	 *
	 * @param key 缓存Key
	 * @return 删除是否成功
	 */
	@Override
	public boolean evictIfPresent(Object key) {
		return this.cache.asMap().remove(key) != null;
	}

	/**
	 * 清空缓存
	 */
	@Override
	public void clear() {
		this.cache.invalidateAll();
	}

	/**
	 * 使缓存失效
	 *
	 * @return 是否执行成功
	 */
	@Override
	public boolean invalidate() {
		boolean notEmpty = !this.cache.asMap().isEmpty();
		this.cache.invalidateAll();
		return notEmpty;
	}

	/**
	 * 判断Key是否存在
	 *
	 * @param key Key
	 * @return 是否存在
	 */
	@Override
	public boolean isExists(Object key) {
		return this.cache.asMap().containsKey(key);
	}

	/**
	 * 是否为 LoadingCache
	 *
	 * @see LoadingCache
	 * @see AsyncLoadingCache
	 * @see com.google.common.cache.LoadingCache
	 */
	@Override
	public boolean isLoadingCache() {
		return cache instanceof LoadingCache || cache instanceof AsyncLoadingCache;
	}
}
