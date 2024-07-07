package com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.alipay.dao;

import cn.bootx.platform.common.mybatisplus.impl.BaseManager;
import cn.bootx.platform.daxpay.service.core.channel.alipay.entity.AliPayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 支付宝配置
 *
 * @author xxm
 * @since 2021/2/26
 */
@Repository
@RequiredArgsConstructor
public class AliPayConfigManager extends BaseManager<AliPayConfigMapper, AliPayConfig> {

}
