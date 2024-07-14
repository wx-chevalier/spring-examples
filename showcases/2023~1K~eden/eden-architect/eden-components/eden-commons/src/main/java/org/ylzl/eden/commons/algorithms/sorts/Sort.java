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

package org.ylzl.eden.commons.algorithms.sorts;

import java.util.List;

/**
 * 排序接口
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public interface Sort {

	/**
	 * 排序数组
	 *
	 * @param array - 未排序的数组
	 * @return 排序后的数组
	 */
	<T extends Comparable<T>> T[] sort(T[] array);

	/**
	 * 排序列表
	 *
	 * @param array - 未排序的列表
	 * @return 排序后的列表
	 */
	<T extends Comparable<T>> List<T> sort(List<T> array);
}
