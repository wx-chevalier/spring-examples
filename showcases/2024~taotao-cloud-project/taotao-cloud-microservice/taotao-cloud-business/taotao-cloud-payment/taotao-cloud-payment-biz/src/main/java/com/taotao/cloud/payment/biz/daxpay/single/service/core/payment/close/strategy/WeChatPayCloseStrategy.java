package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.close.strategy;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.service.core.channel.wechat.entity.WeChatPayConfig;
import cn.bootx.platform.daxpay.service.core.channel.wechat.service.WeChatPayCloseService;
import cn.bootx.platform.daxpay.service.core.channel.wechat.service.WeChatPayConfigService;
import cn.bootx.platform.daxpay.service.func.AbsPayCloseStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 微信支付关闭策略
 * @author xxm
 * @since 2023/12/30
 */
@Slf4j
@Scope(SCOPE_PROTOTYPE)
@Service
@RequiredArgsConstructor
public class WeChatPayCloseStrategy extends AbsPayCloseStrategy {

    private final WeChatPayConfigService weChatPayConfigService;
    private final WeChatPayCloseService weChatPayCloseService;

    private WeChatPayConfig weChatPayConfig;

    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.WECHAT;
    }

    /**
     * 关闭前的处理方式
     */
    @Override
    public void doBeforeCloseHandler() {
        this.weChatPayConfig = weChatPayConfigService.getConfig();
    }

    /**
     * 关闭操作
     */
    @Override
    public void doCloseHandler() {
        weChatPayCloseService.close(this.getOrder(), weChatPayConfig);
    }
}
