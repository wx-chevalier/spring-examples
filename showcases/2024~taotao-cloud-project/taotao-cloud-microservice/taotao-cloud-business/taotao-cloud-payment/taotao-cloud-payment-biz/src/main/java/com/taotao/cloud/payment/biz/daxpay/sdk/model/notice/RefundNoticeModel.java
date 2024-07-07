package com.taotao.cloud.payment.biz.daxpay.sdk.model.notice;

import cn.bootx.platform.daxpay.sdk.code.PayChannelEnum;
import cn.bootx.platform.daxpay.sdk.code.RefundStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 退款通知消息
 * @author xxm
 * @since 2024/2/22
 */
@Getter
@Setter
@ToString
public class RefundNoticeModel {

    /** 退款号 */
    private String refundNo;

    /** 商户退款号 */
    private String bizRefundNo;

    /**
     * 支付通道
     * @see PayChannelEnum
     */
    private String channel;

    /** 退款金额 */
    private Integer amount;

    /**
     * 退款状态
     * @see RefundStatusEnum
     */
    private String status;

    /** 退款成功时间 */
    private Long finishTime;

    /** 退款创建时间 */
    private Long createTime;

    /** 商户扩展参数,回调时会原样返回 */
    private String attach;

    /** 签名 */
    private String sign;
}
