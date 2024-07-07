package com.taotao.cloud.payment.biz.daxpay.single.service.param.record;

import cn.bootx.platform.common.core.rest.param.QueryOrder;
import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.table.modify.annotation.DbColumn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付关闭记录
 * @author xxm
 * @since 2024/1/9
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "支付关闭记录")
public class PayCloseRecordQuery extends QueryOrder {

    /** 订单号 */
    @DbColumn(comment = "订单号")
    private String orderNo;

    /** 商户订单号 */
    @DbColumn(comment = "商户订单号")
    private String bizOrderNo;

    /**
     * 关闭的支付通道
     * @see PayChannelEnum
     */
    @DbColumn(comment = "关闭的异步支付通道")
    private String channel;

    /**
     * 是否关闭成功
     */
    @DbColumn(comment = "是否关闭成功")
    private boolean closed;

    /** 错误码 */
    @DbColumn(comment = "错误码")
    private String errorCode;

    /** 错误消息 */
    @DbColumn(comment = "错误消息")
    private String errorMsg;

    /** 客户端IP */
    @DbColumn(comment = "客户端IP")
    private String clientIp;
}
