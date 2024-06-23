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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * mock配置
 *
 * @author collin
 * @date 2020-12-21
 */
@Getter
@Setter
@ToString
public class MockProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * api mock开关 （默认false）
     */
    private boolean api = false;

    /**
     * method mock开关（默认false）
     */
    private boolean method = false;

    /**
     * 白名单（不走mock）
     */
    private Set<String> whilelist;

}