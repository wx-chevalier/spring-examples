package com.taotao.cloud.mq.example.consumer;


/**
 * @author shuigedeng
 * @since 2024.05
 */
public class ConsumerMain {

    //1. 首先启动消费者，然后启动生产者。
    public static void main(String[] args) {
        final MqConsumerPush mqConsumerPush = new MqConsumerPush();
        mqConsumerPush.start();

        mqConsumerPush.subscribe("TOPIC", "TAGA");
        mqConsumerPush.registerListener(new IMqConsumerListener() {
            @Override
            public ConsumerStatus consumer(MqMessage mqMessage, IMqConsumerListenerContext context) {
                System.out.println("---------- 自定义 " + JSON.toJSONString(mqMessage));
                return ConsumerStatus.SUCCESS;
            }
        });
    }

}
