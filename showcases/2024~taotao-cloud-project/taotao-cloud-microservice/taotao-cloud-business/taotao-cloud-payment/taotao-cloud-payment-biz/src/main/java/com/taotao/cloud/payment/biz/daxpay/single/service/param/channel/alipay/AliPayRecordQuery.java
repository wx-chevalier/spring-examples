package com.taotao.cloud.payment.biz.daxpay.single.service.param.channel.alipay;

import cn.bootx.platform.common.core.annotation.QueryParam;
import cn.bootx.platform.common.core.rest.param.QueryOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付宝支付记录查询参数
 * @author xxm
 * @since 2024/2/20
 */
@QueryParam
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "支付宝支付记录查询参数")
public class AliPayRecordQuery extends QueryOrder {

    @QueryParam(type = QueryParam.CompareTypeEnum.LIKE)
    @Schema(description = "标题")
    private String title;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "本地订单ID")
    private Long orderId;

    @Schema(description = "网关订单号")
    private String gatewayOrderNo;


}
