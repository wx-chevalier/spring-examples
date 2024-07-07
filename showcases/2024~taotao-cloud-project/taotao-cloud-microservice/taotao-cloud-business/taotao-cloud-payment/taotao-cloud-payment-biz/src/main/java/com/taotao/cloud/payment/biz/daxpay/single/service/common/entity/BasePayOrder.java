package com.taotao.cloud.payment.biz.daxpay.single.service.common.entity;

import cn.bootx.platform.common.mybatisplus.base.MpIdEntity;
import cn.bootx.platform.daxpay.code.PayStatusEnum;
import cn.bootx.table.modify.annotation.DbColumn;
import cn.bootx.table.modify.mysql.annotation.DbMySqlIndex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 基础支付订单信息类
 * @author xxm
 * @since 2023/12/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class BasePayOrder extends MpIdEntity {

    /** 交易记录ID */
    @DbColumn(comment = "交易记录ID")
    @DbMySqlIndex(comment = "交易记录ID")
    private Long paymentId;

    /** 关联的业务号 */
    @DbMySqlIndex(comment = "业务号索引")
    @DbColumn(comment = "关联的业务号")
    private String businessNo;

    /** 交易金额 */
    @DbColumn(comment = "交易金额")
    private Integer amount;

    /** 可退款金额 */
    @DbColumn(comment = "可退款金额")
    private Integer refundableBalance;

    /**
     * 支付状态
     * @see PayStatusEnum
     */
    @DbColumn(comment = "支付状态")
    private String status;

    /** 支付时间 */
    @DbColumn(comment = "支付时间")
    private LocalDateTime payTime;
}
