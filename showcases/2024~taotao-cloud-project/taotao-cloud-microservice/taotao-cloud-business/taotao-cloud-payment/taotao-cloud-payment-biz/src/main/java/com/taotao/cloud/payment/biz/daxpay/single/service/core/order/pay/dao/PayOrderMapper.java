package com.taotao.cloud.payment.biz.daxpay.single.service.core.order.pay.dao;

import cn.bootx.platform.daxpay.service.core.order.pay.entity.PayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付订单
 * @author xxm
 * @since 2023/12/18
 */
@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    int insertList(@Param("list")List<PayOrder> list);
}
