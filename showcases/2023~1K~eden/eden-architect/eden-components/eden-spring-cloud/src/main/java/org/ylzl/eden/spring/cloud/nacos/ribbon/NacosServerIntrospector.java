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

package org.ylzl.eden.spring.cloud.nacos.ribbon;

import com.netflix.loadbalancer.Server;
import org.springframework.cloud.netflix.ribbon.DefaultServerIntrospector;

import java.util.Map;

/**
 * NacosServerIntrospector
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public class NacosServerIntrospector extends DefaultServerIntrospector {

	@Override
	public Map<String, String> getMetadata(Server server) {
		if (server instanceof NacosServer) {
			return ((NacosServer) server).getMetadata();
		}
		return super.getMetadata(server);
	}

	@Override
	public boolean isSecure(Server server) {
		if (server instanceof NacosServer) {
			return Boolean.parseBoolean(((NacosServer) server).getMetadata().get("secure"));
		}

		return super.isSecure(server);
	}
}
