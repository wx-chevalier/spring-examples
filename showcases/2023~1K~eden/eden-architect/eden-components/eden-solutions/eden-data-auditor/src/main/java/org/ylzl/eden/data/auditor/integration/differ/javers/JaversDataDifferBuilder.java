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

package org.ylzl.eden.data.auditor.integration.differ.javers;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.ylzl.eden.data.auditor.DataDiffer;
import org.ylzl.eden.data.auditor.builder.AbstractDataDifferBuilder;

/**
 * Javers 数据比对构建器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public class JaversDataDifferBuilder extends AbstractDataDifferBuilder {

	/**
	 * 构建数据比对实例
	 *
	 * @return 数据比对实例
	 */
	@Override
	public DataDiffer build() {
		Javers javers = JaversBuilder.javers()
			.withListCompareAlgorithm(this.getDataDifferConfig().getJavers().getListCompareAlgorithm())
			.withUsePrimitiveDefaults(this.getDataDifferConfig().getJavers().isUsePrimitiveDefaults())
			.build();
		return new JaversDataDiffer(javers);
	}
}
