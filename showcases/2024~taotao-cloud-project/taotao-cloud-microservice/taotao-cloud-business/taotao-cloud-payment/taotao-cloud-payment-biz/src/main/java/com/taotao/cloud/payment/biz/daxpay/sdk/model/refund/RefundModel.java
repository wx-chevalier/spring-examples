package com.taotao.cloud.payment.biz.daxpay.sdk.model.refund;

import cn.bootx.platform.daxpay.sdk.code.RefundStatusEnum;
import cn.bootx.platform.daxpay.sdk.net.DaxPayResponseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 退款响应参数
 * @author xxm
 * @since 2023/12/18
 */
@Getter
@Setter
@ToString
public class RefundModel extends DaxPayResponseModel {

    /** 退款号 */
    private String refundNo;

    /** 商户退款号 */
    private String bizRefundNo;

    /** 退款状态 */
    private String status;
}
