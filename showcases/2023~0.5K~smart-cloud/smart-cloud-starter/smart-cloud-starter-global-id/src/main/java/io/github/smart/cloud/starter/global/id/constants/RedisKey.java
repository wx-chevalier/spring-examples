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
package io.github.smart.cloud.starter.global.id.constants;

/**
 * redis key
 *
 * @author collin
 * @date 2022-03-06
 */
public class RedisKey {

    /**
     * globalid锁key
     */
    public static final String GLOBALID = "smartcloud:lock:globalid";
    /**
     * globalid workerId计算器key
     */
    public static final String GLOBALID_WORKERID = "smartcloud:data:globalid:workerId";

    private RedisKey() {
    }

}