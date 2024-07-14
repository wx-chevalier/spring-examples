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

package org.ylzl.eden.spring.integration.ftpclient.config;

import lombok.Data;
import org.ylzl.eden.commons.env.Charsets;

/**
 * FTP 客户端配置
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Data
public class FTPClientConfig {

	private String host;

	private int port = 21;

	private String username;

	private String password;

	private int connectTimeOut = 5000;

	private int dataTimeout = 60000;

	private String controlEncoding = Charsets.UTF_8_NAME;

	private int controlKeepAliveReplyTimeout = 1000;

	private int bufferSize = 2 << 10;

	private int fileType = 2;

	private boolean useEPSVwithIPv4 = false;

	/**
	 * 是否被动模式
	 */
	private boolean passiveMode = true;
}
