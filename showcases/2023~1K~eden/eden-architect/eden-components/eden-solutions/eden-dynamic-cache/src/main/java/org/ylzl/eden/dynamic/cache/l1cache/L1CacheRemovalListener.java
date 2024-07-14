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

package org.ylzl.eden.dynamic.cache.l1cache;

import org.ylzl.eden.dynamic.cache.Cache;
import org.ylzl.eden.extension.SPI;

/**
 * 一级缓存失效监听器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@SPI("internal")
public interface L1CacheRemovalListener {

	/**
	 * 缓存过期触发
	 *
	 * @param key Key
	 * @param value Value
	 * @param cause 缓存失效原因
	 */
	<K, V> void onRemoval(K key, V value, L1CacheRemovalCause cause);

	/**
	 * 设置缓存实例
	 *
	 * @param cache 缓存实例
	 */
	void setL2Cache(Cache cache);
}
