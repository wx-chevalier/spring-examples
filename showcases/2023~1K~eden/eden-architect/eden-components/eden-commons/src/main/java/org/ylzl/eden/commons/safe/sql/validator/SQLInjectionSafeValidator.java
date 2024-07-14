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

package org.ylzl.eden.commons.safe.sql.validator;

import org.ylzl.eden.commons.safe.SqlSafeUtils;
import org.ylzl.eden.commons.safe.sql.annotation.SQLInjectionSafe;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * SQL 注入安全校验器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public class SQLInjectionSafeValidator implements ConstraintValidator<SQLInjectionSafe, String> {

	@Override
	public void initialize(SQLInjectionSafe sqlInjectionSafe) {
	}

	@Override
	public boolean isValid(String dataString, ConstraintValidatorContext cxt) {
		return SqlSafeUtils.isSQLInjectionSafe(dataString);
	}
}
