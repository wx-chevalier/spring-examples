package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.repair.strategy.pay;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.service.core.channel.wechat.entity.WeChatPayConfig;
import cn.bootx.platform.daxpay.service.core.channel.wechat.service.WeChatPayCloseService;
import cn.bootx.platform.daxpay.service.core.channel.wechat.service.WeChatPayConfigService;
import cn.bootx.platform.daxpay.service.func.AbsPayRepairStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 微信支付订单修复策略类
 * @author xxm
 * @since 2023/12/29
 */
@Slf4j
@Scope(SCOPE_PROTOTYPE)
@Service
@RequiredArgsConstructor
public class WeChatPayRepairStrategy extends AbsPayRepairStrategy {
    private final WeChatPayCloseService closeService;

    private final WeChatPayConfigService weChatPayConfigService;

    private WeChatPayConfig weChatPayConfig;

    /**
     * 策略标识
     */
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.WECHAT;
    }


    /**
     * 修复前处理
     */
    @Override
    public void doBeforeHandler() {
        this.weChatPayConfig = weChatPayConfigService.getConfig();
    }


    /**
     * 关闭本地支付和网关支付
     */
    @Override
    public void doCloseRemoteHandler() {
        closeService.close(this.getOrder(),this.weChatPayConfig);
    }
}
