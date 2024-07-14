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

package org.ylzl.eden.dynamic.cache.integration.hotkey.jd;

import com.jd.platform.hotkey.client.ClientStarter;
import org.ylzl.eden.dynamic.cache.config.CacheConfig;

/**
 * 京东热key探测客户端
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public class JdHotKeyClient {

	/**
	 * 启动客户端
	 *
	 * @param cacheConfig 缓存配置
	 */
	public static void start(CacheConfig cacheConfig) {
		CacheConfig.HotKey.JD jd = cacheConfig.getHotKey().getJd();
		ClientStarter.Builder builder = new ClientStarter.Builder();
		ClientStarter starter = builder
			.setAppName(jd.getAppName())
			.setEtcdServer(jd.getEtcdServer())
			.setPushPeriod(jd.getPushPeriod())
			.setCaffeineSize(jd.getCaffeineSize())
			.build();
		starter.startPipeline();
	}
}
