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
package io.github.smart.cloud.api.core.annotation;

import io.github.smart.cloud.api.core.annotation.constants.ApiAnnotationConstants;

import java.lang.annotation.*;

/**
 * 请求时间戳校验注解
 *
 * @author collin
 * @date 2021-03-28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireTimestamp {

    /**
     * header中请求有效时间间隔（默认2分钟，单位毫秒）
     */
    long validMillis() default ApiAnnotationConstants.DEFAULT_TIMESTAMP_VALID_MILLIS;

}