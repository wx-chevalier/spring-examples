package com.taotao.cloud.payment.biz.daxpay.single.service.core.system.config.dao;

import cn.bootx.platform.common.mybatisplus.impl.BaseManager;
import cn.bootx.platform.daxpay.service.core.system.config.entity.PlatformConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 平台配置e
 * @author xxm
 * @since 2023/12/24
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PlatformConfigManager extends BaseManager<PlatformConfigMapper, PlatformConfig> {
}
