package com.taotao.cloud.payment.biz.daxpay.single.service.core.order.refund.convert;

import cn.bootx.platform.daxpay.result.order.RefundOrderResult;
import cn.bootx.platform.daxpay.service.core.order.refund.entity.RefundOrder;
import cn.bootx.platform.daxpay.service.core.order.refund.entity.RefundOrderExtra;
import cn.bootx.platform.daxpay.service.dto.order.refund.RefundOrderDto;
import cn.bootx.platform.daxpay.service.dto.order.refund.RefundOrderExtraDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author xxm
 * @since 2022/3/2
 */
@Mapper
public interface RefundOrderConvert {

    RefundOrderConvert CONVERT = Mappers.getMapper(RefundOrderConvert.class);

    RefundOrderDto convert(RefundOrder in);

    RefundOrderExtraDto convert(RefundOrderExtra in);

    RefundOrderResult convertResult(RefundOrder in);


}
