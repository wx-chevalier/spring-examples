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
import java.nio.charset.StandardCharsets;

/**
 * 多语言配置信息
 *
 * @author collin
 * @date 2019-07-15
 */
@Getter
@Setter
@ToString
public class LocaleProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码（默认utf-8）
     */
    private String encoding = StandardCharsets.UTF_8.name();
    /**
     * 缓存时间（默认7天；单位秒）
     */
    private int cacheSeconds = 60 * 60 * 24 * 7;

}