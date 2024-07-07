package com.taotao.cloud.payment.biz.daxpay.single.service.core.system.config.convert;

import cn.bootx.platform.daxpay.service.core.system.config.entity.PayApiConfig;
import cn.bootx.platform.daxpay.service.dto.system.config.PayApiConfigDto;
import cn.bootx.platform.daxpay.service.param.system.config.PayApiConfigParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 开放接口信息转换
 * @author xxm
 * @since 2023/12/22
 */
@Mapper
public interface PayApiConfigConvert {
    PayApiConfigConvert CONVERT = Mappers.getMapper(PayApiConfigConvert.class);

    PayApiConfig convert(PayApiConfigParam in);

    PayApiConfigDto convert(PayApiConfig in);
}
