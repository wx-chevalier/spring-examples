package com.taotao.cloud.payment.biz.daxpay.single.service.core.order.pay.dao;

import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.pay.entity.PayChannelOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通道支付订单
 * @author xxm
 * @since 2023/12/20
 */
@Mapper
public interface PayChannelOrderMapper extends BaseMapper<PayChannelOrder> {
}
