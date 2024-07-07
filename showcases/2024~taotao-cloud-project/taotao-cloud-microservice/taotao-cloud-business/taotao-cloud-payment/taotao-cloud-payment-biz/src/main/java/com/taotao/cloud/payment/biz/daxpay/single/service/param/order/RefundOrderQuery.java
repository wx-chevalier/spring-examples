package com.taotao.cloud.payment.biz.daxpay.single.service.param.order;

import cn.bootx.platform.common.core.annotation.QueryParam;
import cn.bootx.platform.common.core.rest.param.QueryOrder;
import cn.bootx.platform.daxpay.code.RefundStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付退款查询参数
 * @author xxm
 * @since 2024/1/9
 */
@EqualsAndHashCode(callSuper = true)
@Data
@QueryParam(type = QueryParam.CompareTypeEnum.EQ)
@Accessors(chain = true)
@Schema(title = "支付退款查询参数")
public class RefundOrderQuery extends QueryOrder {

    @Schema(description = "退款Id")
    private Long id;

    @Schema(description = "退款号")
    private String refundNo;

    @Schema(description = "支付ID")
    private Long paymentId;

    @Schema(description = "关联的业务号")
    @QueryParam(type = QueryParam.CompareTypeEnum.LIKE)
    private String businessNo;

    @Schema(description = "支付网关订单号")
    private String gatewayOrderNo;

    @Schema(description = "标题")
    @QueryParam(type = QueryParam.CompareTypeEnum.LIKE)
    private String title;

    /**
     * @see RefundStatusEnum
     */
    @Schema(description = "退款状态")
    private String status;
}
