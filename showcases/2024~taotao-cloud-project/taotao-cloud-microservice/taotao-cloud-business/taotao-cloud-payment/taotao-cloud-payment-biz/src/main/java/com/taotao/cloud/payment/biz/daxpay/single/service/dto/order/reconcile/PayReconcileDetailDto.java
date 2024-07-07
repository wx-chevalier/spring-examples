package com.taotao.cloud.payment.biz.daxpay.single.service.dto.order.reconcile;

import cn.bootx.platform.common.core.rest.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 对账订单详情
 * @author xxm
 * @since 2024/1/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "对账订单详情")
public class PayReconcileDetailDto extends BaseDto {

    /** 关联对账订单ID */
    @Schema(description = "关联对账订单ID")
    private Long recordOrderId;

    /** 商品名称 */
    @Schema(description = "商品名称")
    private String title;

    /** 交易金额 */
    @Schema(description = "交易金额")
    private Integer amount;

    /** 交易类型 pay/refund */
    @Schema(description = "交易类型")
    private String type;

    /** 本地订单ID */
    @Schema(description = "本地订单ID")
    private String paymentId;

    /** 本地退款ID */
    @Schema(description = "本地退款ID")
    private String refundId;

    /** 网关订单号 - 支付宝/微信的订单号 */
    @Schema(description = "网关订单号")
    private String gatewayOrderNo;
}
