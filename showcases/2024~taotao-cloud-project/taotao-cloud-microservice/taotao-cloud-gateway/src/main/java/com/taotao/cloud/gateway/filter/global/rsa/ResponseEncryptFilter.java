package com.taotao.cloud.gateway.filter.global.rsa;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.taotao.cloud.common.utils.secure.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import com.taotao.cloud.common.utils.log.LogUtils;
//@Component
@Slf4j
public class ResponseEncryptFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("============================ResponseEncryptFilter start===================================");

        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String url = uri.getPath();

		HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
        if(Objects.equals(statusCode, HttpStatus.BAD_REQUEST) || Objects.equals(statusCode, HttpStatus.TOO_MANY_REQUESTS)){
            // 如果是特殊的请求，已处理响应内容，这里不再处理
            return chain.filter(exchange);
        }

        // 根据具体业务内容，修改响应体
        return modifyResponseBody(exchange, chain);
    }

    /**
     * 修改响应体
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> modifyResponseBody(ServerWebExchange exchange, GatewayFilterChain chain)  {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator response = buildResponse(originalResponse, bufferFactory);
        return chain.filter(exchange.mutate().response(response).build());
    }


    @Override
    public int getOrder() {
        return 5;
    }
    private ServerHttpResponseDecorator buildResponse(ServerHttpResponse originalResponse, DataBufferFactory bufferFactory) {
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffers);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        DataBufferUtils.release(join);
                        // 流转为字符串
                        String responseData = new String(content, Charsets.UTF_8);
                        LogUtils.info(responseData);

                        Map map = JSON.parseObject(responseData);
                        //处理返回的数据
                        Object encrypt = map.get("encrypt");
                        if(encrypt!=null){
                            log.info("加密响应数据 开始 ：{}",responseData);
                            //加密数据
                            responseData = RSAUtils.encrypt(responseData,RSAConstant.PUBLICK_KEY);
                            log.info("加密响应数据 完成 ：{}",responseData);
                        }

                        byte[] uppedContent = responseData.getBytes(Charsets.UTF_8);
                        originalResponse.getHeaders().setContentLength(uppedContent.length);
                        return bufferFactory.wrap(uppedContent);
                    }));
                } else {
                    log.error("获取响应体数据 ："+getStatusCode());
                }
                return super.writeWith(body);
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };
    }
}

