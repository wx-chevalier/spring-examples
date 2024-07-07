package com.taotao.cloud.rpc.client.client.support.fail.enums;

/**
 * 失败类型枚举
 * @author shuigedeng
 * @since 0.1.1
 */
public enum FailTypeEnum {
    /**
     * 快速失败
     * @since 0.1.1
     */
    FAIL_FAST(1),
    /**
     * 失败重试
     * 选择另外一个 channel 进行重试
     * @since 0.1.1
     */
    FAIL_OVER(2),
    /**
     * 失败之后不进行报错，直接返回
     * @since 0.1.1
     */
    FAIL_SAFE(3),
    ;

    private final int code;

    FailTypeEnum(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "FailTypeEnum{" +
                "code=" + code +
                '}';
    }

}
