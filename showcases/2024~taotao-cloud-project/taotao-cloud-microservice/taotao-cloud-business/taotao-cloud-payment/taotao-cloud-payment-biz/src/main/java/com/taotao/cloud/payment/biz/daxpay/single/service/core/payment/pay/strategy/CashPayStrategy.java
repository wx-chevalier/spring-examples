package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.pay.strategy;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.exception.pay.PayAmountAbnormalException;
import cn.bootx.platform.daxpay.param.pay.PayChannelParam;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.cash.service.CashRecordService;
import com.taotao.cloud.payment.biz.daxpay.single.service.func.AbsPayStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 现金支付
 *
 * @author xxm
 * @since 2021/6/23
 */
@Slf4j
@Scope(SCOPE_PROTOTYPE)
@Service
@RequiredArgsConstructor
public class CashPayStrategy extends AbsPayStrategy {

    private final CashRecordService cashRecordService;

    /**
     * 现金支付
     */
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.CASH;
    }

    /**
     * 支付前检查
     */
    @Override
    public void doBeforePayHandler() {
        // 检查金额
        PayChannelParam payChannelParam = this.getPayChannelParam();
        if (payChannelParam.getAmount() <= 0) {
            throw new PayAmountAbnormalException();
        }
    }

    /**
     * 支付操作
     */
    @Override
    public void doPayHandler() {
        cashRecordService.pay(this.getChannelOrder(),this.getOrder().getTitle());
    }

}
