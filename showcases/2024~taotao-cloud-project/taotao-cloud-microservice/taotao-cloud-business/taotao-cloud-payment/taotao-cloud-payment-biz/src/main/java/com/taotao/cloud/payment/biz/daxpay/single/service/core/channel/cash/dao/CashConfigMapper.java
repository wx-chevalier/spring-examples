package com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.cash.dao;

import com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.cash.entity.CashConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 现金支付配置
 * @author xxm
 * @since 2024/2/17
 */
@Mapper
public interface CashConfigMapper extends BaseMapper<CashConfig> {
}
