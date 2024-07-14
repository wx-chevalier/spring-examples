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

package org.ylzl.eden.dynamic.mq.spring.boot.env;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息队列配置
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Data
@ConfigurationProperties(prefix = MessageQueueProperties.PREFIX)
public class MessageQueueProperties {

	public static final String PREFIX = "spring.message-queue.dynamic";

	private boolean enabled;

	private String primary;

	private final RocketMQ rocketMQ = new RocketMQ();

	private final Kafka kafka = new Kafka();

	@Setter
	@Getter
	public static class RocketMQ {

		public static final String PREFIX = MessageQueueProperties.PREFIX + ".rocketmq";

		private boolean enabled;
	}

	@Setter
	@Getter
	public static class Kafka {

		public static final String PREFIX = MessageQueueProperties.PREFIX + ".kafka";

		private boolean enabled;
	}
}
