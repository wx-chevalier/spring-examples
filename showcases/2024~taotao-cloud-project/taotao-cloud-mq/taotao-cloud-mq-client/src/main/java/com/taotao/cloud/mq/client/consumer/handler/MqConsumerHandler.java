package com.taotao.cloud.mq.client.consumer.handler;

import com.alibaba.fastjson2.JSON;
import com.taotao.cloud.mq.client.consumer.api.IMqConsumerListenerContext;
import com.taotao.cloud.mq.client.consumer.support.listener.IMqListenerService;
import com.taotao.cloud.mq.client.consumer.support.listener.MqConsumerListenerContext;
import com.taotao.cloud.mq.common.constant.MethodType;
import com.taotao.cloud.mq.common.dto.req.MqMessage;
import com.taotao.cloud.mq.common.dto.resp.MqCommonResp;
import com.taotao.cloud.mq.common.dto.resp.MqConsumerResultResp;
import com.taotao.cloud.mq.common.resp.ConsumerStatus;
import com.taotao.cloud.mq.common.resp.MqCommonRespCode;
import com.taotao.cloud.mq.common.resp.MqException;
import com.taotao.cloud.mq.common.rpc.RpcMessageDto;
import com.taotao.cloud.mq.common.support.invoke.IInvokeService;
import com.taotao.cloud.mq.common.util.ChannelUtil;
import com.taotao.cloud.mq.common.util.DelimiterUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author shuigedeng
 * @since 2024.05
 */
public class MqConsumerHandler extends SimpleChannelInboundHandler {

    private static final Logger log = LoggerFactory.getLogger(MqConsumerHandler.class);

    /**
     * 调用管理类
     * @since 2024.05
     */
    private IInvokeService invokeService;

    /**
     * 消息监听服务类
     * @since 2024.05
     */
    private IMqListenerService mqListenerService;

    public void setInvokeService(IInvokeService invokeService) {
        this.invokeService = invokeService;
    }

    public void setMqListenerService(IMqListenerService mqListenerService) {
        this.mqListenerService = mqListenerService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        RpcMessageDto rpcMessageDto = null;
        try {
            rpcMessageDto = JSON.parseObject(bytes, RpcMessageDto.class);
        } catch (Exception exception) {
//            log.error("RpcMessageDto json 格式转换异常 {}", new String(bytes));
            return;
        }

        if (rpcMessageDto.isRequest()) {
            MqCommonResp commonResp = this.dispatch(rpcMessageDto, ctx);

            if(commonResp == null) {
                log.debug("当前消息为 null，忽略处理。");
                return;
            }

            writeResponse(rpcMessageDto, commonResp, ctx);
        } else {
            final String traceId = rpcMessageDto.getTraceId();

            // 丢弃掉 traceId 为空的信息
//            if(StringUtil.isBlank(traceId)) {
//                log.debug("[Server Response] response traceId 为空，直接丢弃", JSON.toJSON(rpcMessageDto));
//                return;
//            }

            // 添加消息
            invokeService.addResponse(traceId, rpcMessageDto);
        }
    }

    /**
     * 消息的分发
     *
     * @param rpcMessageDto 入参
     * @param ctx 上下文
     * @return 结果
     */
    private MqCommonResp dispatch(RpcMessageDto rpcMessageDto, ChannelHandlerContext ctx) {
        final String methodType = rpcMessageDto.getMethodType();
        final String json = rpcMessageDto.getJson();

        String channelId = ChannelUtil.getChannelId(ctx);
//        log.debug("channelId: {} 接收到 method: {} 内容：{}", channelId,
//                methodType, json);

        // 消息发送
        if(MethodType.B_MESSAGE_PUSH.equals(methodType)) {
            // 日志输出
//            log.info("收到服务端消息: {}", json);
            return this.consumer(json);
        }

        throw new UnsupportedOperationException("暂不支持的方法类型");
    }

    /**
     * 消息消费
     * @param json 原始请求
     * @return 结果
     * @since 2024.05
     */
    private MqCommonResp consumer(final String json) {
        try {
            // 如果是 broker，应该进行处理化等操作。
            MqMessage mqMessage = JSON.parseObject(json, MqMessage.class);
            IMqConsumerListenerContext context = new MqConsumerListenerContext();
            ConsumerStatus consumerStatus = this.mqListenerService.consumer(mqMessage, context);

            MqConsumerResultResp resp = new MqConsumerResultResp();
            resp.setRespCode(MqCommonRespCode.SUCCESS.getCode());
            resp.setRespMessage(MqCommonRespCode.SUCCESS.getMsg());
            resp.setConsumerStatus(consumerStatus.getCode());
            return resp;
        } catch (MqException mqException) {
            log.error("消息消费业务异常", mqException);
            MqConsumerResultResp resp = new MqConsumerResultResp();
            resp.setRespCode(mqException.getCode());
            resp.setRespMessage(mqException.getMsg());
            return resp;
        } catch (Exception exception) {
            log.error("消息消费系统异常", exception);
            MqConsumerResultResp resp = new MqConsumerResultResp();
            resp.setRespCode(MqCommonRespCode.FAIL.getCode());
            resp.setRespMessage(MqCommonRespCode.FAIL.getMsg());
            return resp;
        }
    }

    /**
     * 结果写回
     *
     * @param req  请求
     * @param resp 响应
     * @param ctx  上下文
     */
    private void writeResponse(RpcMessageDto req,
                               Object resp,
                               ChannelHandlerContext ctx) {
        final String id = ctx.channel().id().asLongText();

        RpcMessageDto rpcMessageDto = new RpcMessageDto();
        // 响应类消息
        rpcMessageDto.setRequest(false);
        rpcMessageDto.setTraceId(req.getTraceId());
        rpcMessageDto.setMethodType(req.getMethodType());
        rpcMessageDto.setRequestTime(System.currentTimeMillis());
        String json = JSON.toJSONString(resp);
        rpcMessageDto.setJson(json);

        // 回写到 client 端
        ByteBuf byteBuf = DelimiterUtil.getMessageDelimiterBuffer(rpcMessageDto);
        ctx.writeAndFlush(byteBuf);
//        log.debug("[Server] channel {} response {}", id, JSON.toJSON(rpcMessageDto));
    }


}
