package com.taotao.cloud.payment.biz.daxpay.sdk.net;

import cn.bootx.platform.daxpay.sdk.code.SignTypeEnum;
import lombok.Builder;
import lombok.Getter;

/**
 * 支付配置
 * @author xxm
 * @since 2024/2/2
 */
@Getter
@Builder
public class DaxPayConfig {

    /** 服务地址 */
    private String serviceUrl;

    /** 签名方式 */
    @Builder.Default
    private SignTypeEnum signType = SignTypeEnum.MD5;

    /** 签名秘钥 */
    private String signSecret;

    /** 请求超时时间 */
    @Builder.Default
    private int reqTimeout = 30000;
}
