
package com.taotao.cloud.rpc.registry.register.core;

import com.taotao.cloud.rpc.common.common.remote.netty.handler.ChannelHandlers;
import com.taotao.cloud.rpc.common.common.remote.netty.impl.DefaultNettyServer;
import io.netty.channel.ChannelHandler;

/**
 * 默认注册中心配置
 * @author shuigedeng
 * @since 2024.06
 */
public class RegisterBs implements com.taotao.cloud.rpc.registry.register.api.config.RegisterConfig {

    /**
     * 服务启动端口信息
     * @since 2024.06
     */
    private int port;

    /**
     * 服务端
     */
    private final com.taotao.cloud.rpc.registry.register.simple.server.RegisterServerService registerServerService;
    /**
     * 客户端
     */
    private final com.taotao.cloud.rpc.registry.register.simple.client.RegisterClientService registerClientService;

    private RegisterBs(){
        registerServerService = new com.taotao.cloud.rpc.registry.register.simple.server.impl.DefaultRegisterServerService();
        registerClientService = new com.taotao.cloud.rpc.registry.register.simple.client.impl.DefaultRegisterClientService();
    }

    public static RegisterBs newInstance() {
        RegisterBs registerBs = new RegisterBs();
        registerBs.port(8527);
        return registerBs;
    }

    @Override
    public RegisterBs port(int port) {
//        ArgUtil.notNegative(port, "port");

        this.port = port;
        return this;
    }

    @Override
    public RegisterBs start() {
        ChannelHandler channelHandler = ChannelHandlers.objectCodecHandler(new com.taotao.cloud.rpc.registry.register.simple.handler.RegisterCenterServerHandler());
        DefaultNettyServer.newInstance(port, channelHandler).asyncRun();

        // 通知对应的服务端和客户端，服务启动。
        // 新增的时候暂时不处理。
        // 暂时注册中心的是无状态的，无法获取到没有访问过的节点。（如果访问过，则客户端肯定已经有对应的信息。）
        // 如果使用 redis/database 等集中式的存储，或者进行数据同步，则有通知的必要性。

        // 添加对应的 shutdown hook
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                new com.taotao.cloud.rpc.registry.register.support.hook.RegisterCenterShutdownHook(registerServerService, registerClientService, port).hook();
//            }
//        });

        return this;
    }

}
