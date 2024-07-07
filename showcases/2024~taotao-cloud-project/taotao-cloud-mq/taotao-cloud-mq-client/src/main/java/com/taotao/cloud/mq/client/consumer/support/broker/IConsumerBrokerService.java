package com.taotao.cloud.mq.client.consumer.support.broker;

import com.taotao.cloud.mq.common.api.Destroyable;
import com.taotao.cloud.mq.common.dto.req.MqCommonReq;
import com.taotao.cloud.mq.common.dto.req.component.MqConsumerUpdateStatusDto;
import com.taotao.cloud.mq.common.dto.resp.MqCommonResp;
import com.taotao.cloud.mq.common.dto.resp.MqConsumerPullResp;
import com.taotao.cloud.mq.common.resp.ConsumerStatus;
import io.netty.channel.Channel;
import java.util.List;

/**
 * @author shuigedeng
 * @since 2024.05
 */
public interface IConsumerBrokerService extends Destroyable {

	/**
	 * 初始化列表
	 *
	 * @param config 配置
	 * @since 2024.05
	 */
	void initChannelFutureList(final ConsumerBrokerConfig config);

	/**
	 * 注册到服务端
	 *
	 * @since 2024.05
	 */
	void registerToBroker();

	/**
	 * 调用服务端
	 *
	 * @param channel   调用通道
	 * @param commonReq 通用请求
	 * @param respClass 类
	 * @param <T>       泛型
	 * @param <R>       结果
	 * @return 结果
	 * @since 2024.05
	 */
	<T extends MqCommonReq, R extends MqCommonResp> R callServer(Channel channel,
		T commonReq,
		Class<R> respClass);

	/**
	 * 获取请求通道
	 *
	 * @param key 标识
	 * @return 结果
	 * @since 2024.05
	 */
	Channel getChannel(String key);

	/**
	 * 订阅
	 *
	 * @param topicName    topic 名称
	 * @param tagRegex     标签正则
	 * @param consumerType 消费者类型
	 */
	void subscribe(String topicName, String tagRegex, String consumerType);

	/**
	 * 取消订阅
	 *
	 * @param topicName    topic 名称
	 * @param tagRegex     标签正则
	 * @param consumerType 消费者类型
	 */
	void unSubscribe(String topicName, String tagRegex, String consumerType);

	/**
	 * 心跳
	 *
	 * @since 2024.05
	 */
	void heartbeat();

	/**
	 * 拉取消息
	 *
	 * @param topicName 标题名称
	 * @param tagRegex  标签正则
	 * @param fetchSize 大小
	 * @return 结果
	 */
	MqConsumerPullResp pull(String topicName,
		String tagRegex,
		int fetchSize);

	/**
	 * 消费状态回执
	 *
	 * @param messageId      消息唯一标识
	 * @param consumerStatus 消费状态
	 * @return 结果
	 * @since 2024.05
	 */
	MqCommonResp consumerStatusAck(String messageId,
		ConsumerStatus consumerStatus);

	/**
	 * 消费状态回执-批量
	 *
	 * @param statusDtoList 状态列表
	 * @return 结果
	 * @since 2024.05
	 */
	MqCommonResp consumerStatusAckBatch(List<MqConsumerUpdateStatusDto> statusDtoList);

}
