package com.taotao.cloud.rpc.common.common.exception;

/**
 * rpc 超时异常
 *
 * @author shuigedeng
 * @since 2024.06
 */
public class RpcTimeoutException extends RuntimeException {

    private static final long serialVersionUID = -2521814477982789832L;

    public RpcTimeoutException() {
    }

    public RpcTimeoutException(String message) {
        super(message);
    }

    public RpcTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcTimeoutException(Throwable cause) {
        super(cause);
    }

    public RpcTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
