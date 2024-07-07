package com.taotao.cloud.payment.biz.daxpay.sdk.param.channel;

import cn.bootx.platform.daxpay.sdk.param.ChannelParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 付款码支付
 * @author xxm
 * @since 2024/2/26
 */
@Getter
@Setter
public class BarCodePayParam implements ChannelParam {

    /** 授权码(主动扫描用户的付款码) */
    private String authCode;
}
