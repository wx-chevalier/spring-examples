package com.taotao.cloud.mq.client.producer.support.broker;


import com.taotao.cloud.mq.common.rpc.RpcChannelFuture;
import com.taotao.cloud.mq.common.support.invoke.IInvokeService;
import com.taotao.cloud.mq.common.support.status.IStatusManager;
import com.taotao.cloud.mq.common.tmp.ILoadBalance;

/**
 * @author shuigedeng
 * @since 2024.05
 */
public class ProducerBrokerConfig {

	/**
	 * 分组名称
	 */
	private String groupName;

	/**
	 * 中间人地址
	 */
	private String brokerAddress;

	/**
	 * 调用管理服务
	 *
	 * @since 2024.05
	 */
	private IInvokeService invokeService;

	/**
	 * 获取响应超时时间
	 *
	 * @since 2024.05
	 */
	private long respTimeoutMills;

	/**
	 * 检测 broker 可用性
	 *
	 * @since 2024.05
	 */
	private boolean check;

	/**
	 * 状态管理
	 *
	 * @since 2024.05
	 */
	private IStatusManager statusManager;

	/**
	 * 负载均衡
	 *
	 * @since 2024.05
	 */
	private ILoadBalance<RpcChannelFuture> loadBalance;

	/**
	 * 最大尝试次数
	 *
	 * @since 2024.05
	 */
	private int maxAttempt;

	/**
	 * 账户标识
	 *
	 * @since 2024.05
	 */
	private String appKey;

	/**
	 * 账户密码
	 *
	 * @since 2024.05
	 */
	private String appSecret;

	public static ProducerBrokerConfig newInstance() {
		return new ProducerBrokerConfig();
	}

	public String appKey() {
		return appKey;
	}

	public ProducerBrokerConfig appKey(String appKey) {
		this.appKey = appKey;
		return this;
	}

	public String appSecret() {
		return appSecret;
	}

	public ProducerBrokerConfig appSecret(String appSecret) {
		this.appSecret = appSecret;
		return this;
	}

	public int maxAttempt() {
		return maxAttempt;
	}

	public ProducerBrokerConfig maxAttempt(int maxAttempt) {
		this.maxAttempt = maxAttempt;
		return this;
	}

	public String groupName() {
		return groupName;
	}

	public ProducerBrokerConfig groupName(String groupName) {
		this.groupName = groupName;
		return this;
	}

	public String brokerAddress() {
		return brokerAddress;
	}

	public ProducerBrokerConfig brokerAddress(String brokerAddress) {
		this.brokerAddress = brokerAddress;
		return this;
	}

	public IInvokeService invokeService() {
		return invokeService;
	}

	public ProducerBrokerConfig invokeService(IInvokeService invokeService) {
		this.invokeService = invokeService;
		return this;
	}

	public long respTimeoutMills() {
		return respTimeoutMills;
	}

	public ProducerBrokerConfig respTimeoutMills(long respTimeoutMills) {
		this.respTimeoutMills = respTimeoutMills;
		return this;
	}

	public boolean check() {
		return check;
	}

	public ProducerBrokerConfig check(boolean check) {
		this.check = check;
		return this;
	}

	public IStatusManager statusManager() {
		return statusManager;
	}

	public ProducerBrokerConfig statusManager(IStatusManager statusManager) {
		this.statusManager = statusManager;
		return this;
	}

	public ILoadBalance<RpcChannelFuture> loadBalance() {
		return loadBalance;
	}

	public ProducerBrokerConfig loadBalance(ILoadBalance<RpcChannelFuture> loadBalance) {
		this.loadBalance = loadBalance;
		return this;
	}
}
