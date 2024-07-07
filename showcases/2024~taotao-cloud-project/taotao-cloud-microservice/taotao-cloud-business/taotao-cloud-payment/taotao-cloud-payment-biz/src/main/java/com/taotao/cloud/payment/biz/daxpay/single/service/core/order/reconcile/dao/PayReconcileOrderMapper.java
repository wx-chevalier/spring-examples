package com.taotao.cloud.payment.biz.daxpay.single.service.core.order.reconcile.dao;

import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.reconcile.entity.PayReconcileOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author xxm
 * @since 2024/1/18
 */
@Mapper
public interface PayReconcileOrderMapper extends BaseMapper<PayReconcileOrder> {
}
