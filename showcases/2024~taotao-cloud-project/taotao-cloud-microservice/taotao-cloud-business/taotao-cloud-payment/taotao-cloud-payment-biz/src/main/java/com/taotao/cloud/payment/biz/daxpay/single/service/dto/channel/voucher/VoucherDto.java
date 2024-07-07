package com.taotao.cloud.payment.biz.daxpay.single.service.dto.channel.voucher;

import cn.bootx.platform.common.core.rest.dto.BaseDto;
import com.taotao.cloud.payment.biz.daxpay.single.service.code.VoucherCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xxm
 * @since 2022/3/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "储值卡")
public class VoucherDto extends BaseDto {

    @Schema(description = "卡号")
    private String cardNo;

    @Schema(description = "面值")
    private BigDecimal faceValue;

    @Schema(description = "余额")
    private BigDecimal balance;

    @Schema(description = "是否长期有效")
    private Boolean enduring;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    /**
     * @see VoucherCode#STATUS_FORBIDDEN
     */
    @Schema(description = "状态")
    private String status;

}
