package com.taotao.cloud.payment.biz.daxpay.single.service.core.order.refund.dao;

import cn.bootx.platform.daxpay.service.core.order.refund.entity.RefundOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xxm
 * @since 2022/3/2
 */
@Mapper
public interface RefundOrderMapper extends BaseMapper<RefundOrder> {

}
