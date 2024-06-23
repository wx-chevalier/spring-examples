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
package io.github.smart.cloud.starter.rabbitmq.test.prepare.configuration;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.github.fridujo.rabbitmq.mock.exchange.MockExchangeCreator;
import io.github.smart.cloud.starter.rabbitmq.MqConstants;
import io.github.smart.cloud.starter.rabbitmq.test.prepare.constants.MqConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        MockConnectionFactory mockConnectionFactory = new MockConnectionFactory();
        mockConnectionFactory.withAdditionalExchange(MockExchangeCreator.creatorWithExchangeType(MqConstants.DELAY_MESSAGE_TYPE, DelayExchange::new));
        return new CachingConnectionFactory(mockConnectionFactory);
    }

    @Bean
    public Queue sendCouponQueue() {
        return new Queue(MqConstant.SendCoupon.QUEUE, true);
    }

    @Bean
    public Exchange sendCouponExchange() {
        return ExchangeBuilder.directExchange(MqConstant.SendCoupon.EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding sendCouponBinding() {
        return BindingBuilder.bind(sendCouponQueue()).to(sendCouponExchange()).with(MqConstant.SendCoupon.ROUTING).noargs();
    }

}