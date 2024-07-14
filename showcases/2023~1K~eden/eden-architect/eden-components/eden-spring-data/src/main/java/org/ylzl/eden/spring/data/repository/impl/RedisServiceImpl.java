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

package org.ylzl.eden.spring.data.repository.impl;

import org.ylzl.eden.spring.data.redis.repository.RedisRepository;
import org.ylzl.eden.spring.data.repository.RedisService;

import java.io.Serializable;

/**
 * Redis 业务实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@SuppressWarnings("unchecked")
public class RedisServiceImpl<T, ID extends Serializable> extends CrudServiceImpl<T, ID>
	implements RedisService<T, ID> {

	private final RedisRepository<T, ID> redisRepository;

	public RedisServiceImpl(RedisRepository<T, ID> redisRepository) {
		super(redisRepository);
		this.redisRepository = redisRepository;
	}
}
