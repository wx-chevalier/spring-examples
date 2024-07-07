package com.taotao.cloud.payment.biz.daxpay.sdk.model.pay;

import cn.bootx.platform.daxpay.sdk.code.PayChannelEnum;
import cn.bootx.platform.daxpay.sdk.code.PayStatusEnum;
import cn.bootx.platform.daxpay.sdk.net.DaxPayResponseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 发起支付后响应对象
 * @author xxm
 * @since 2024/2/2
 */
@Getter
@Setter
@ToString
public class PayModel extends DaxPayResponseModel {
    /** 商户订单号 */
    private String bizOrderNo;

    /** 订单号 */
    private String orderNo;

    /** 支付状态 */
    private String status;

    /** 支付参数体(通常用于发起支付的参数) */
    private String payBody;
}
