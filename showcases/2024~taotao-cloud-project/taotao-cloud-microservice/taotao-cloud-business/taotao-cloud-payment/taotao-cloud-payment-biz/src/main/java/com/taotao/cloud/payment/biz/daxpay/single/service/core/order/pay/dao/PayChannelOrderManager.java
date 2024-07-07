package com.taotao.cloud.payment.biz.daxpay.single.service.core.order.pay.dao;

import cn.bootx.platform.common.mybatisplus.impl.BaseManager;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.pay.entity.PayChannelOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 支付订单关联支付时通道信息
 * @author xxm
 * @since 2023/12/20
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PayChannelOrderManager extends BaseManager<PayChannelOrderMapper, PayChannelOrder> {

    /**
     * 根据订单查找
     */
    public List<PayChannelOrder> findAllByPaymentId(Long paymentId){
        return findAllByField(PayChannelOrder::getPaymentId,paymentId);
    }

    /**
     * 根据订单id和支付通道查询
     */
    public Optional<PayChannelOrder> findByPaymentIdAndChannel(Long paymentId, String channel) {
        return lambdaQuery()
                .eq(PayChannelOrder::getPaymentId,paymentId)
                .eq(PayChannelOrder::getChannel,channel)
                .oneOpt();
    }

    /**
     * 根据订单id删除异步支付记录
     */
    public void deleteByPaymentIdAndAsync(Long paymentId){
        lambdaUpdate()
               .eq(PayChannelOrder::getPaymentId,paymentId)
               .eq(PayChannelOrder::isAsync,true)
               .remove();

    }
}
