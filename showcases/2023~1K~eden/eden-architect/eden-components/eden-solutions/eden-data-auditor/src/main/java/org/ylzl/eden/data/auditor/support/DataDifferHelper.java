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

package org.ylzl.eden.data.auditor.support;

import org.ylzl.eden.data.auditor.DataDiffer;
import org.ylzl.eden.data.auditor.builder.DataDifferBuilder;
import org.ylzl.eden.extension.ExtensionLoader;

/**
 * 数据比对帮助支持
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public class DataDifferHelper {

	/**
	 * 获取数据比对实例
	 *
	 * @return 数据比对实例
	 */
	public static DataDiffer dataDiffer() {
		return ExtensionLoader.getExtensionLoader(DataDifferBuilder.class).getDefaultExtension().build();
	}

	/**
	 * 获取数据比对实例
	 *
	 * @param spi 扩展点
	 * @return 数据比对实例
	 */
	public static DataDiffer dataDiffer(String spi) {
		return ExtensionLoader.getExtensionLoader(DataDifferBuilder.class).getExtension(spi).build();
	}
}
