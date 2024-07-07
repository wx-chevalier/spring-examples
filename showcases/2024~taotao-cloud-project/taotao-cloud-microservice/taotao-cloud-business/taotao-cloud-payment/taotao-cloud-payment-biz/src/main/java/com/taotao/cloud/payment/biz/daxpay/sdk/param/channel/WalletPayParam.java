package com.taotao.cloud.payment.biz.daxpay.sdk.param.channel;

import cn.bootx.platform.daxpay.sdk.param.ChannelParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 钱包支付参数
 *
 * @author xxm
 * @since 2020/12/8
 */
@Getter
@Setter
public class WalletPayParam implements ChannelParam {

    /** 钱包ID */
    private Long walletId;

    /** 用户ID */
    private String userId;

}
