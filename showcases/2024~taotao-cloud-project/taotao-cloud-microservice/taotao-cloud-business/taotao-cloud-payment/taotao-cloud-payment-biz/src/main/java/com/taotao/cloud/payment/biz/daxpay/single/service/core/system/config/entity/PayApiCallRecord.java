package com.taotao.cloud.payment.biz.daxpay.single.service.core.system.config.entity;

import cn.bootx.platform.common.mybatisplus.base.MpCreateEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付接口调用记录
 * @author xxm
 * @since 2023/12/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
//@DbTable(comment = "支付接口调用记录")
@TableName("pay_api_call_record")
public class PayApiCallRecord extends MpCreateEntity {
}
