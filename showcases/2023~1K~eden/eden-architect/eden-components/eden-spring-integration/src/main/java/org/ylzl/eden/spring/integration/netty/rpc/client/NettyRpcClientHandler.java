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

package org.ylzl.eden.spring.integration.netty.rpc.client;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.ylzl.eden.spring.integration.netty.rpc.model.RpcRequest;
import org.ylzl.eden.spring.integration.netty.rpc.model.RpcResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Netty RPC 客户端处理器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public class NettyRpcClientHandler extends ChannelDuplexHandler {

	private final Map<String, NettyRpcFuture> rpcFutureMap = new ConcurrentHashMap<>();

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
		throws Exception {
		if (msg instanceof RpcRequest) {
			RpcRequest request = (RpcRequest) msg;
			rpcFutureMap.putIfAbsent(request.getRequestId(), new NettyRpcFuture());
		}
		super.write(ctx, msg, promise);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof RpcResponse) {
			RpcResponse response = (RpcResponse) msg;
			NettyRpcFuture future = rpcFutureMap.get(response.getRequestId());
			future.set(response);
		}
		super.channelRead(ctx, msg);
	}

	public RpcResponse get(String requestId, int timeout) {
		if (!rpcFutureMap.containsKey(requestId)) {
			return null;
		}
		try {
			NettyRpcFuture future = rpcFutureMap.get(requestId);
			return future.get(timeout);
		} finally {
			rpcFutureMap.remove(requestId);
		}
	}
}
