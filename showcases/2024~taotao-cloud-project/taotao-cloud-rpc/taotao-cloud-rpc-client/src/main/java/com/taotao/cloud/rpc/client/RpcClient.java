package com.taotao.cloud.rpc.client;

import com.taotao.cloud.rpc.common.common.RpcDecoder;
import com.taotao.cloud.rpc.common.common.RpcEncoder;
import com.taotao.cloud.rpc.common.common.RpcReponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import com.taotao.cloud.rpc.common.common.RpcReponse;
import com.taotao.cloud.rpc.common.common.RpcRequest;
/**
 * client<br>
 *
 * @author shuigedeng
 * @version v1.0.0
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcReponse> {

	private RpcReponse response;
	private final Object object = new Object();
	private String ip;
	private int port;

	public RpcClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	/**
	 * 发送数据
	 *
	 * @param request request
	 * @return com.taotao.rpc.common.RpcReponse
	 * @author shuigedeng
	 * @date 2024.06
	 */
	public RpcReponse send(RpcRequest request) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap
				.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
							.addLast(new RpcEncoder(RpcRequest.class))
							.addLast(new RpcDecoder(RpcReponse.class))
							.addLast(RpcClient.this);
					}
				})
				.option(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
			// 发送request请求
			channelFuture.channel().writeAndFlush(request).sync();

			// 等待讲数据读取完毕 阻塞线程
			synchronized (object) {
				object.wait();
			}

			if (null != response) {
				channelFuture.channel().closeFuture().sync();
			}

			return response;
		}
		finally {
			group.shutdownGracefully();
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcReponse response) throws Exception {
		this.response = response;
		// 当数据获取完毕 通知主线程释放
		synchronized (object) {
			object.notifyAll();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
