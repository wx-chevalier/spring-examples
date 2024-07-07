package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.repair.strategy.refund;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.service.core.channel.cash.service.CashRecordService;
import cn.bootx.platform.daxpay.service.func.AbsRefundRepairStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 现金退款修复策略
 * @author xxm
 * @since 2024/1/30
 */
@Slf4j
@Scope(SCOPE_PROTOTYPE)
@Service
@RequiredArgsConstructor
public class CashRefundRepairStrategy extends AbsRefundRepairStrategy {
    private final CashRecordService cashRecordService;

    /**
     * 策略标识
     */
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.CASH;
    }

    /**
     * 退款成功修复
     */
    @Override
    public void doSuccessHandler() {
        cashRecordService.refund(this.getRefundChannelOrder(),this.getPayOrder().getTitle());
    }
}
