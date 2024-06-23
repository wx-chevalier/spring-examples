/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.smart.cloud.starter.rabbitmq.autoconfigure;

import io.github.smart.cloud.starter.rabbitmq.RabbitMqConsumerFailRetryBeanProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mq消费失败重试配置
 *
 * @author collin
 * @date 2021-06-30
 */
@Configuration
@AutoConfigureBefore(RabbitAutoConfiguration.class)
public class RabbitMqConsumerFailRetryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RabbitMqConsumerFailRetryBeanProcessor rabbitMqConsumerFailRetryBeanProcessor() {
        return new RabbitMqConsumerFailRetryBeanProcessor();
    }

}