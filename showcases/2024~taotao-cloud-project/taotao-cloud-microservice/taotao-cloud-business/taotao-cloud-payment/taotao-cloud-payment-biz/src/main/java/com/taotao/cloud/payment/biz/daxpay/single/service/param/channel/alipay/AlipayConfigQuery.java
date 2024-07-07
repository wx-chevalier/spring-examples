package com.taotao.cloud.payment.biz.daxpay.single.service.param.channel.alipay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xxm
 * @since 2021/7/22
 */
@Data
@Accessors(chain = true)
@Schema(title = "支付宝配置搜索参数")
public class AlipayConfigQuery implements Serializable {

    private static final long serialVersionUID = -173325268481050362L;

    /** 名称 */
    private String name;

    /** 状态 */
    private Integer state;

    /** 支付宝商户appId */
    private String appId;

}
