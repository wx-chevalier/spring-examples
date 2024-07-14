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

package org.ylzl.eden.dynamic.sms.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 短信模型
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Data
public class Sms {

	/**
	 * 手机号码
	 */
	private String phoneNumber;

	/**
	 * 短信内容
	 */
	private String smsContent;

	/**
	 * 自定义短信ID
	 */
	private String customSmsId;

	/**
	 * 扩展代码
	 */
	private String extendedCode;

	/**
	 * 定时发送时间（值为空表示立即发送）
	 */
	private String timerTime;
}
