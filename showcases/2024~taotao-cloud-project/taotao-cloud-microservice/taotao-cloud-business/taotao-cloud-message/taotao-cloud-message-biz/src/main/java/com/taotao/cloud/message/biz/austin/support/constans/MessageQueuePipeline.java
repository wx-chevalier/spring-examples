package com.taotao.cloud.message.biz.austin.support.constans;


/**
 * 消息队列常量
 *
 * @author 3y
 */
public interface MessageQueuePipeline {
    String EVENT_BUS = "eventBus";
    String KAFKA = "kafka";
    String ROCKET_MQ = "rocketMq";
    String RABBIT_MQ = "rabbitMq";
    String SPRING_EVENT_BUS = "springEventBus";

}
