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
package io.github.smart.cloud.starter.configure.properties;

import io.github.smart.cloud.constants.LogLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * feign日志切面配置
 *
 * @author collin
 * @date 2019-06-19
 */
@Getter
@Setter
@ToString
public class FeignLogProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * feign切面开关 （默认true）
     */
    private boolean enable = true;

    /**
     * openfeign日志级别（默认DEBUG）
     *
     * @see LogLevel
     */
    private String level = LogLevel.DEBUG;

    /**
     * 慢接口时间（单位：毫秒，默认3000毫秒）
     */
    private int slowApiMinCost = 3000;

}