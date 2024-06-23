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

import io.github.smart.cloud.exception.AcquiredLockFailException;
import io.github.smart.cloud.starter.redis.annotation.RedisLock;
import io.github.smart.cloud.starter.redis.constants.RedisLockConstants;
import io.github.smart.cloud.starter.redis.enums.RedisKeyPrefix;
import org.aopalliance.intercept.MethodInvocation;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * 分布式锁拦截器
 *
 * @author collin
 * @date 2022-02-02
 * @see RedisLock
 */
public class RedisLockInterceptor extends AbstractRedisInterceptor implements Ordered {

    public RedisLockInterceptor(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public int getOrder() {
        // 将优先级设置为最高，以便与数据库事务注解同时使用时，redis锁拦截器能先执行
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String lockName = getKey(RedisKeyPrefix.LOCK.getKey(), redisLock.prefix(), redisLock.expressions(), method, invocation.getArguments());
        RLock lock = redissonClient.getLock(lockName);
        boolean isRequiredLock = false;
        try {
            if (redisLock.leaseTime() == RedisLockConstants.DEFAULT_LEASE_TIME) {
                isRequiredLock = lock.tryLock(redisLock.waitTime(), redisLock.unit());
            } else {
                isRequiredLock = lock.tryLock(redisLock.waitTime(), redisLock.leaseTime(), redisLock.unit());
            }
            if (!isRequiredLock) {
                throw new AcquiredLockFailException(redisLock.acquiredFailCode());
            }

            return invocation.proceed();
        } finally {
            if (isRequiredLock) {
                lock.unlock();
            }
        }
    }

}