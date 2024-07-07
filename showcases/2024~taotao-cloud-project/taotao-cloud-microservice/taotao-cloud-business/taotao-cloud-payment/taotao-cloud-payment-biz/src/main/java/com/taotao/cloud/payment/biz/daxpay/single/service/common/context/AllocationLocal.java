package com.taotao.cloud.payment.biz.daxpay.single.service.common.context;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分账上下文
 * @author xxm
 * @since 2024/4/15
 */
@Data
@Accessors(chain = true)
public class AllocationLocal {

    /** 三方系统分账号 */
    private String outAllocationNo;
}
