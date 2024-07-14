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

package org.ylzl.eden.dynamic.cache.support.value;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 封装 Null，避免缓存组件将 Null 视为过期 Key 清除
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
@EqualsAndHashCode
public final class NullValue implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Object INSTANCE = new NullValue();

	private NullValue() {
	}

	private Object readResolve() {
		return INSTANCE;
	}

	@Override
	public String toString() {
		return "null";
	}
}
