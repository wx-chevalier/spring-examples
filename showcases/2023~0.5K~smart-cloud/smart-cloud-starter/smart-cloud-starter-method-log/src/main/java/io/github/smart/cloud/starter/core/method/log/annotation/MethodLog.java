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
package io.github.smart.cloud.starter.core.method.log.annotation;

import io.github.smart.cloud.constants.LogLevel;

import java.lang.annotation.*;

/**
 * method日志打印注解
 *
 * @author collin
 * @date 2021-03-13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {

    /**
     * 日志级别（默认debug级别；如果要覆盖全局配置，需要指定注解的日志级别）
     *
     * @return
     * @see LogLevel#getFinalLevel
     */
    String level() default "";

}