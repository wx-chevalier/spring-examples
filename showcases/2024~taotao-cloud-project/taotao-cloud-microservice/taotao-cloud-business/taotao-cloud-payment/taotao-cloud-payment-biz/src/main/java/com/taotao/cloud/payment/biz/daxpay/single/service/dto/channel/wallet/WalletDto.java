package com.taotao.cloud.payment.biz.daxpay.single.service.dto.channel.wallet;

import cn.bootx.platform.common.core.rest.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xxm
 * @since 2020/12/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "钱包")
public class WalletDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -1563719305334334625L;

    @Schema(description = "钱包关联的账号ID")
    private String userId;

    /** 钱包名称 */
    @Schema(description = "钱包名称")
    private String name;

    @Schema(description = "钱包余额")
    private BigDecimal balance;

    @Schema(description = "状态")
    private String status;

}
