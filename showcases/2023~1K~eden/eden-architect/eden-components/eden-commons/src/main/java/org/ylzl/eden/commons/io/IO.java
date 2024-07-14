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

package org.ylzl.eden.commons.io;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

/**
 * IO 常量
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public class IO {

	public static final String DIR_SEPARATOR_UNIX = String.valueOf(IOUtils.DIR_SEPARATOR_UNIX);

	public static final String DIR_SEPARATOR_WINDOWS = String.valueOf(IOUtils.DIR_SEPARATOR_WINDOWS);

	public static final String DIR_SEPARATOR = String.valueOf(IOUtils.DIR_SEPARATOR);

	public static final String PARENT_DIR_SEPARATOR = ".." + DIR_SEPARATOR;

	public static final String LINE_SEPARATOR_UNIX = IOUtils.LINE_SEPARATOR_UNIX;

	public static final String LINE_SEPARATOR_WINDOWS = IOUtils.LINE_SEPARATOR_WINDOWS;
}
