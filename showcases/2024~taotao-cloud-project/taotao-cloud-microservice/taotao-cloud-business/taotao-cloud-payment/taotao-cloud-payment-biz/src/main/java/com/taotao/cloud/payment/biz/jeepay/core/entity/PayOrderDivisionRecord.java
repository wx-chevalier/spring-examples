package com.taotao.cloud.payment.biz.jeepay.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 分账记录表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2021-08-19
 */
@ApiModel(value = "分账记录表", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pay_order_division_record")
public class PayOrderDivisionRecord implements Serializable {

    public static final byte STATE_WAIT = 0; // 待分账
    public static final byte STATE_SUCCESS = 1; // 分账成功（明确成功）
    public static final byte STATE_FAIL = 2; // 分账失败（明确失败）
    public static final byte STATE_ACCEPT = 3; // 分账已受理（上游受理）

    private static final long serialVersionUID=1L;

    public static final LambdaQueryWrapper<PayOrderDivisionRecord> gw(){
        return new LambdaQueryWrapper<>();
    }

    /**
     * 分账记录ID
     */
    @ApiModelProperty(value = "分账记录ID")
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;

    /**
     * 商户号
     */
    @ApiModelProperty(value = "商户号")
    private String mchNo;

    /**
     * 服务商号
     */
    @ApiModelProperty(value = "服务商号")
    private String isvNo;

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID")
    private String appId;

    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户名称")
    private String mchName;

    /**
     * 类型: 1-普通商户, 2-特约商户(服务商模式)
     */
    @ApiModelProperty(value = "类型: 1-普通商户, 2-特约商户(服务商模式)")
    private Byte mchType;

    /**
     * 支付接口代码
     */
    @ApiModelProperty(value = "支付接口代码")
    private String ifCode;

    /**
     * 系统支付订单号
     */
    @ApiModelProperty(value = "系统支付订单号")
    private String payOrderId;

    /**
     * 支付订单渠道支付订单号
     */
    @ApiModelProperty(value = "支付订单渠道支付订单号")
    private String payOrderChannelOrderNo;

    /**
     * 订单金额,单位分
     */
    @ApiModelProperty(value = "订单金额,单位分")
    private Long payOrderAmount;

    /**
     * 订单实际分账金额, 单位：分（订单金额 - 商户手续费 - 已退款金额）
     */
    @ApiModelProperty(value = "订单实际分账金额, 单位：分（订单金额 - 商户手续费 - 已退款金额）")
    private Long payOrderDivisionAmount;

    /**
     * 系统分账批次号
     */
    @ApiModelProperty(value = "系统分账批次号")
    private String batchOrderId;

    /**
     * 上游分账批次号
     */
    @ApiModelProperty(value = "上游分账批次号")
    private String channelBatchOrderId;

    /**
     * 状态: 0-待分账 1-分账成功, 2-分账失败
     */
    @ApiModelProperty(value = "状态: 0-待分账 1-分账成功, 2-分账失败")
    private Byte state;

    /**
     * 上游返回数据包
     */
    @ApiModelProperty(value = "上游返回数据包")
    private String channelRespResult;

    /**
     * 账号快照》 分账接收者ID
     */
    @ApiModelProperty(value = "账号快照》 分账接收者ID")
    private Long receiverId;

    /**
     * 账号快照》 组ID（便于商户接口使用）
     */
    @ApiModelProperty(value = "账号快照》 组ID（便于商户接口使用）")
    private Long receiverGroupId;

    /**
     * 账号快照》 分账接收者别名
     */
    @ApiModelProperty(value = "账号快照》 分账接收者别名")
    private String receiverAlias;

    /**
     * 账号快照》 分账接收账号类型: 0-个人 1-商户
     */
    @ApiModelProperty(value = "账号快照》 分账接收账号类型: 0-个人 1-商户")
    private Byte accType;

    /**
     * 账号快照》 分账接收账号
     */
    @ApiModelProperty(value = "账号快照》 分账接收账号")
    private String accNo;

    /**
     * 账号快照》 分账接收账号名称
     */
    @ApiModelProperty(value = "账号快照》 分账接收账号名称")
    private String accName;

    /**
     * 账号快照》 分账关系类型（参考微信）， 如： SERVICE_PROVIDER 服务商等
     */
    @ApiModelProperty(value = "账号快照》 分账关系类型（参考微信）， 如： SERVICE_PROVIDER 服务商等")
    private String relationType;

    /**
     * 账号快照》 当选择自定义时，需要录入该字段。 否则为对应的名称
     */
    @ApiModelProperty(value = "账号快照》 当选择自定义时，需要录入该字段。 否则为对应的名称")
    private String relationTypeName;

    /**
     * 账号快照》 配置的实际分账比例
     */
    @ApiModelProperty(value = "账号快照》 配置的实际分账比例")
    private BigDecimal divisionProfit;

    /**
     * 计算该接收方的分账金额,单位分
     */
    @ApiModelProperty(value = "计算该接收方的分账金额,单位分")
    private Long calDivisionAmount;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
