package com.taotao.cloud.sys.integration.sku.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.taotao.cloud.common.utils.log.LogUtils;
import com.taotao.cloud.goods.api.grpc.BooleanReply;
import com.taotao.cloud.goods.api.grpc.ConditionsRequest;
import com.taotao.cloud.goods.api.grpc.GoodsSkuServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class SkuGrpcClient {

	@Autowired
	private DiscoveryClient discoveryClient;

	public BooleanReply getGoodsSkuByIdFromCache(String name) {
		try {
			final ServiceInstance instanceInfo = discoveryClient.getInstances("my-service-name").get(0);
			final ManagedChannel channel = ManagedChannelBuilder.forAddress(
					instanceInfo.getUri().toString(), instanceInfo.getPort())
				.usePlaintext()
				.build();
			final GoodsSkuServiceGrpc.GoodsSkuServiceFutureStub stub = GoodsSkuServiceGrpc.newFutureStub(channel);
			ConditionsRequest helloRequest =  ConditionsRequest.parseFrom(ByteString.copyFromUtf8(name));
			ListenableFuture<BooleanReply> helloReplyListenableFuture = stub.getGoodsSkuByIdFromCache(helloRequest);
			return helloReplyListenableFuture.get();
		}
		catch (InvalidProtocolBufferException | InterruptedException | ExecutionException e) {
			LogUtils.error("sayHello---------------");
			throw new RuntimeException(e);
		}
	}
}
