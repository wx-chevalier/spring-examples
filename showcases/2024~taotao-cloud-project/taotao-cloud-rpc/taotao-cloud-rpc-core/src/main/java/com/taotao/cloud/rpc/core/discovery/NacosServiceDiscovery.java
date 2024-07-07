package com.taotao.cloud.rpc.core.discovery;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.taotao.cloud.rpc.common.exception.ObtainServiceException;
import com.taotao.cloud.rpc.common.exception.RpcException;
import com.taotao.cloud.rpc.common.loadbalancer.LoadBalancer;
import com.taotao.cloud.rpc.common.util.NacosUtils;
import java.net.InetSocketAddress;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * nacos 服务发现
 */
@Slf4j
public class NacosServiceDiscovery extends ServiceDiscovery {

	/**
	 * 兼容 SPI 机制
	 */
	public NacosServiceDiscovery() {
	}

	public NacosServiceDiscovery(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	@Override
	public void setLoadBalancer(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	/**
	 * 以默认组名"DEFAULT_GROUP", 查询匹配服务名的服务网络套接字地址
	 *
	 * @param serviceName 服务名
	 * @return 网络套接字地址
	 * @throws RpcException
	 */
	@Override
	public InetSocketAddress lookupService(String serviceName) throws RpcException {
		try {
			List<Instance> instances = NacosUtils.getAllInstance(serviceName);
			Instance instance = loadBalancer.selectService(instances);
			log.debug("lookupService: ip [{}], port [{}]", instance.getIp(), instance.getPort());
			return new InetSocketAddress(instance.getIp(), instance.getPort());
		} catch (NacosException e) {
			log.error("error occurred while fetching the service:{}", e.getMessage());
			throw new ObtainServiceException("error occurred while fetching the service Exception");
		} catch (RpcException e) {
			log.error(
				"service instances size is zero, can't provide service! please start server first! Exception: {}",
				e.getMessage());
			throw e;
		}
	}

	/**
	 * 查询匹配组名下服务名的服务网络套接字地址
	 *
	 * @param serviceName 服务名
	 * @param groupName   组名
	 * @return 网络套接字地址
	 * @throws RpcException
	 */
	@Override
	public InetSocketAddress lookupService(String serviceName, String groupName)
		throws RpcException {
		try {
			List<Instance> instances = NacosUtils.getAllInstance(serviceName, groupName);
			Instance instance = loadBalancer.selectService(instances);
			log.debug("lookupService: ip [{}], port [{}]", instance.getIp(), instance.getPort());
			return new InetSocketAddress(instance.getIp(), instance.getPort());
		} catch (NacosException e) {
			log.error("error occurred while fetching the service:{}", e.getMessage());
			throw new ObtainServiceException("error occurred while fetching the service Exception");
		} catch (RpcException e) {
			log.error(
				"service instances size is zero, can't provide service! please start server first! Exception: {}",
				e.getMessage());
			throw e;
		}
	}
}
