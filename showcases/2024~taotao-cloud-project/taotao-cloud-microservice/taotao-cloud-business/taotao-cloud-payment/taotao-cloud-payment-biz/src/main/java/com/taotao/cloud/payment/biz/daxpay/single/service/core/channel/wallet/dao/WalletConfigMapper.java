package com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.wallet.dao;

import cn.bootx.platform.daxpay.service.core.channel.wallet.entity.WalletConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 钱包配置
 * @author xxm
 * @since 2023/7/14
 */
@Mapper
public interface WalletConfigMapper extends BaseMapper<WalletConfig> {
}
