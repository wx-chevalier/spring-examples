package com.taotao.cloud.payment.biz.daxpay.single.service.dto.order.refund;

import cn.bootx.platform.common.core.rest.dto.BaseDto;
import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.code.RefundStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 退款记录
 *
 * @author xxm
 * @since 2022/3/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "退款记录")
public class RefundOrderDto extends BaseDto {

    /** 支付订单ID */
    @Schema(description = "支付订单ID")
    private Long orderId;

    /** 支付订单号 */
    @Schema(description = "支付订单号")
    private String orderNo;

    /** 商户支付订单号 */
    @Schema(description = "商户支付订单号")
    private String bizOrderNo;

    /** 支付标题 */
    @Schema(description = "支付标题")
    private String title;

    /** 退款号 */
    @Schema(description = "退款号")
    private String refundNo;

    @Schema(description = "商户退款号")
    private String bizRefundNo;

    /** 三方支付系统退款交易号 */
    @Schema(description = "三方支付系统退款交易号")
    private String outRefundNo;

    /**
     * 退款通道
     * @see PayChannelEnum
     */
    @Schema(description = "支付通道")
    private String channel;

    /** 订单金额 */
    @Schema(description = "订单金额")
    private Integer orderAmount;

    /** 退款金额 */
    @Schema(description = "退款金额")
    private Integer amount;

    /** 退款原因 */
    @Schema(description = "退款原因")
    private String reason;

    @Schema(description = "退款结束时间")
    private LocalDateTime finishTime;

    /**
     * 退款状态
     * @see RefundStatusEnum
     */
    @Schema(description = "退款状态")
    private String status;

    /** 错误码 */
    @Schema(description = "错误码")
    private String errorCode;

    /** 错误信息 */
    @Schema(description = "错误信息")
    private String errorMsg;

}
