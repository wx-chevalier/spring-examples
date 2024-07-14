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

package org.ylzl.eden.zuul.spring.cloud.autoconfigure;

import com.netflix.zuul.ZuulFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.ylzl.eden.zuul.spring.cloud.actuate.ZuulRouteEndpoint;

/**
 * Zuul 端点自动装配
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@ConditionalOnBean({RouteLocator.class, DiscoveryClient.class})
@ConditionalOnClass(ZuulFilter.class)
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Configuration(proxyBeanMethods = false)
public class ZuulEndpointAutoConfiguration {

	private static final String MSG_AUTOWIRED_ZUUL_ENDPOINT = "Autowired Zuul Endpoint";

	@ConditionalOnAvailableEndpoint
	@ConditionalOnMissingBean
	@Bean
	public ZuulRouteEndpoint zuulRouteEndpoint(RouteLocator routeLocator, DiscoveryClient discoveryClient) {
		log.debug(MSG_AUTOWIRED_ZUUL_ENDPOINT);
		return new ZuulRouteEndpoint(routeLocator, discoveryClient);
	}
}
