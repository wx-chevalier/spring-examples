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
package io.github.smart.cloud.starter.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存方法的执行结果
 *
 * @author collin
 * @date 2022-03-11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cacheable {

    /**
     * 缓存一级分类名称
     *
     * @return
     */
    String name();

    /**
     * 缓存的变量key
     *
     * @return
     */
    String[] expressions();

    /**
     * 缓存有效期
     *
     * @return
     */
    long ttl();

    /**
     * 缓存有效期时间单位
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;

}