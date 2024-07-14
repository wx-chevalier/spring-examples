/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ylzl.eden.commons.json.jackson.mapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Jackson 默认对象映射器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public class DefaultObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 8090216975101285238L;

	public DefaultObjectMapper() {
		super();

		// 序列化时忽略NULL
		this.setSerializationInclusion(Include.NON_NULL);
		// 反序列化时忽略未知属性
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 禁用空对象转换校验
		this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
}
