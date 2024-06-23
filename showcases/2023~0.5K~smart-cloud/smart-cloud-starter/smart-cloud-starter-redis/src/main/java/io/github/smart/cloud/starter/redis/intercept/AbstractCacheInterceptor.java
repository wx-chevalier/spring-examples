/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.smart.cloud.starter.redis.intercept;

import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 缓存父类
 *
 * @author collin
 * @date 2022-03-11
 */
public abstract class AbstractCacheInterceptor extends AbstractRedisInterceptor {

    protected final RedisTemplate<Object, Object> redisTemplate;

    protected AbstractCacheInterceptor(RedisTemplate<Object, Object> redisTemplate, RedissonClient redissonClient) {
        super(redissonClient);
        this.redisTemplate = redisTemplate;
    }

}