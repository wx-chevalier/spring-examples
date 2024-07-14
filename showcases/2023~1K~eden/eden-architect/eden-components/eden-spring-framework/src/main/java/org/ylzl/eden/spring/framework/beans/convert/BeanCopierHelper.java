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

package org.ylzl.eden.spring.framework.beans.convert;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

/**
 * Bean 拷贝工具集
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public class BeanCopierHelper {

	public static <S, T> T copy(@NonNull S source, @NonNull T target) {
		return copy(source, target, null);
	}

	public static <S, T> T copy(@NonNull S source, @NonNull T target, Converter converter) {
		BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), converter != null);
		copier.copy(source, target, converter);
		return target;
	}
}
