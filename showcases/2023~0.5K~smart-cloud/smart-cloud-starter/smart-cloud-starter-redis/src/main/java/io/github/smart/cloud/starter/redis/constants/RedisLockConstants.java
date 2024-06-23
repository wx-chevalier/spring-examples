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
package io.github.smart.cloud.starter.redis.constants;

import io.github.smart.cloud.starter.redis.annotation.RedisLock;

/**
 * {@link RedisLock}常量
 *
 * @author collin
 * @date 2022-02-04
 */
public class RedisLockConstants {

    /**
     * 默认的获取锁等待时间（5000毫秒）
     */
    public static final long DEFAULT_WAIT_TIME = 5000L;

    /**
     * 默认的锁释放时间，-1表示直到代码执行完毕才释放锁
     */
    public static final long DEFAULT_LEASE_TIME = -1L;

    private RedisLockConstants() {
    }

}