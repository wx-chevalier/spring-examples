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

package org.ylzl.eden.commons.algorithms.searches;

import lombok.NonNull;

/**
 * 二分查找
 *
 * <p>时间复杂度：最好为 O(1)，最差为 O(log n)，平均为 O(log n)
 *
 * <p>空间复杂度：最差为 O(1)
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public class BinarySearch implements Search {

	/**
	 * 查找
	 *
	 * @param array 数组
	 * @param key   查找关键字
	 * @return 匹配值
	 */
	@Override
	public <T extends Comparable<T>> int search(@NonNull T[] array, @NonNull T key) {
		return search(array, key, 0, array.length);
	}

	private <T extends Comparable<T>> int search(T[] array, T key, int left, int right) {
		if (right < left) {
			return -1;
		}

		int median = (left + right) >>> 1;
		int comp = key.compareTo(array[median]);
		if (comp == 0) {
			return median;
		} else if (comp < 0) {
			return search(array, key, left, --median);
		} else {
			return search(array, key, ++median, right);
		}
	}
}
