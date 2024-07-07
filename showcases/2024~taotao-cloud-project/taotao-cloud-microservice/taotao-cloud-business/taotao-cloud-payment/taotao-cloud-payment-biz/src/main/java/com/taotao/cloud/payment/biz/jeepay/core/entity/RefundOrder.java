/*
 * Copyright (c) 2021-2031, 河北计全科技有限公司 (https://www.jeequan.com & jeequan@126.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.payment.biz.jeepay.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jeequan.jeepay.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 退款订单表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
@ApiModel(value = "退款订单表", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_refund_order")
public class RefundOrder extends BaseModel {

    public static final byte STATE_INIT = 0; //订单生成
    public static final byte STATE_ING = 1; //退款中
    public static final byte STATE_SUCCESS = 2; //退款成功
    public static final byte STATE_FAIL = 3; //退款失败
    public static final byte STATE_CLOSED = 4; //退款任务关闭

    public static final LambdaQueryWrapper<RefundOrder> gw(){
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID=1L;

    /**
     * 退款订单号（支付系统生成订单号）
     */
    @ApiModelProperty(value = "退款订单号（支付系统生成订单号）")
    @TableId
    private String refundOrderId;

    /**
     * 支付订单号（与t_pay_order对应）
     */
    @ApiModelProperty(value = "支付订单号（与t_pay_order对应）")
    private String payOrderId;

    /**
     * 渠道支付单号（与t_pay_order channel_order_no对应）
     */
    @ApiModelProperty(value = "渠道支付单号（与t_pay_order channel_order_no对应）")
    private String channelPayOrderNo;

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
     * 商户退款单号（商户系统的订单号）
     */
    @ApiModelProperty(value = "商户退款单号（商户系统的订单号）")
    private String mchRefundNo;

    /**
     * 支付方式代码
     */
    @ApiModelProperty(value = "支付方式代码")
    private String wayCode;

    /**
     * 支付接口代码
     */
    @ApiModelProperty(value = "支付接口代码")
    private String ifCode;

    /**
     * 支付金额,单位分
     */
    @ApiModelProperty(value = "支付金额,单位分")
    private Long payAmount;

    /**
     * 退款金额,单位分
     */
    @ApiModelProperty(value = "退款金额,单位分")
    private Long refundAmount;

    /**
     * 三位货币代码,人民币:cny
     */
    @ApiModelProperty(value = "三位货币代码,人民币:cny")
    private String currency;

    /**
     * 退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-退款任务关闭
     */
    @ApiModelProperty(value = "退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-退款任务关闭")
    private Byte state;

    /**
     * 客户端IP
     */
    @ApiModelProperty(value = "客户端IP")
    private String clientIp;

    /**
     * 退款原因
     */
    @ApiModelProperty(value = "退款原因")
    private String refundReason;

    /**
     * 渠道订单号
     */
    @ApiModelProperty(value = "渠道订单号")
    private String channelOrderNo;

    /**
     * 渠道错误码
     */
    @ApiModelProperty(value = "渠道错误码")
    private String errCode;

    /**
     * 渠道错误描述
     */
    @ApiModelProperty(value = "渠道错误描述")
    private String errMsg;

    /**
     * 特定渠道发起时额外参数
     */
    @ApiModelProperty(value = "特定渠道发起时额外参数")
    private String channelExtra;

    /**
     * 通知地址
     */
    @ApiModelProperty(value = "通知地址")
    private String notifyUrl;

    /**
     * 扩展参数
     */
    @ApiModelProperty(value = "扩展参数")
    private String extParam;

    /**
     * 订单退款成功时间
     */
    @ApiModelProperty(value = "订单退款成功时间")
    private Date successTime;

    /**
     * 退款失效时间（失效后系统更改为退款任务关闭状态）
     */
    @ApiModelProperty(value = "退款失效时间（失效后系统更改为退款任务关闭状态）")
    private Date expiredTime;

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
