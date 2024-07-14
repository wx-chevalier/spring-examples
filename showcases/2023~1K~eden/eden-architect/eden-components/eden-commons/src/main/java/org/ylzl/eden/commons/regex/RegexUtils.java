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
package org.ylzl.eden.commons.regex;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public class RegexUtils {

	public static boolean isMatch(@NonNull String regex, @NonNull CharSequence input) {
		return Pattern.matches(regex, input);
	}

	public static boolean find(@NonNull String regex, @NonNull CharSequence input) {
		return Pattern.compile(regex, Pattern.MULTILINE).matcher(input).find();
	}

	public static List<String> group(@NonNull String regex, @NonNull CharSequence input) {
		List<String> matches = new ArrayList<>();
		Matcher matcher = Pattern.compile(regex).matcher(input);
		int i = 1;
		while (matcher.find()) {
			matches.add(matcher.group(i));
			i++;
		}
		return matches;
	}

	public static String replaceAll(
		@NonNull String regex, @NonNull CharSequence input, @NonNull String replacement) {
		return Pattern.compile(regex).matcher(input).replaceAll(replacement);
	}

	public static String replaceFirst(
		@NonNull String regex, @NonNull CharSequence input, @NonNull String replacement) {
		return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
	}
}
