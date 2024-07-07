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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 转账订单表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2021-08-11
 */
@ApiModel(value = "转账订单表", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_transfer_order")
public class TransferOrder implements Serializable {

    /** 入账方式 **/
    public static final String ENTRY_WX_CASH = "WX_CASH";
    public static final String ENTRY_ALIPAY_CASH = "ALIPAY_CASH";
    public static final String ENTRY_BANK_CARD = "BANK_CARD";

    public static final byte STATE_INIT = 0; //订单生成
    public static final byte STATE_ING = 1; //转账中
    public static final byte STATE_SUCCESS = 2; //转账成功
    public static final byte STATE_FAIL = 3; //转账失败
    public static final byte STATE_CLOSED = 4; //转账关闭


    public static final LambdaQueryWrapper<TransferOrder> gw(){
        return new LambdaQueryWrapper<>();
    }


    private static final long serialVersionUID=1L;

    /**
     * 转账订单号
     */
    @ApiModelProperty(value = "转账订单号")
    @TableId
    private String transferId;

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
     * 商户订单号
     */
    @ApiModelProperty(value = "商户订单号")
    private String mchOrderNo;

    /**
     * 支付接口代码
     */
    @ApiModelProperty(value = "支付接口代码")
    private String ifCode;

    /**
     * 入账方式： WX_CASH-微信零钱; ALIPAY_CASH-支付宝转账; BANK_CARD-银行卡
     */
    @ApiModelProperty(value = "入账方式： WX_CASH-微信零钱; ALIPAY_CASH-支付宝转账; BANK_CARD-银行卡")
    private String entryType;

    /**
     * 转账金额,单位分
     */
    @ApiModelProperty(value = "转账金额,单位分")
    private Long amount;

    /**
     * 三位货币代码,人民币:cny
     */
    @ApiModelProperty(value = "三位货币代码,人民币:cny")
    private String currency;

    /**
     * 收款账号
     */
    @ApiModelProperty(value = "收款账号")
    private String accountNo;

    /**
     * 收款人姓名
     */
    @ApiModelProperty(value = "收款人姓名")
    private String accountName;

    /**
     * 收款人开户行名称
     */
    @ApiModelProperty(value = "收款人开户行名称")
    private String bankName;

    /**
     * 转账备注信息
     */
    @ApiModelProperty(value = "转账备注信息")
    private String transferDesc;

    /**
     * 客户端IP
     */
    @ApiModelProperty(value = "客户端IP")
    private String clientIp;

    /**
     * 支付状态: 0-订单生成, 1-转账中, 2-转账成功, 3-转账失败, 4-订单关闭
     */
    @ApiModelProperty(value = "支付状态: 0-订单生成, 1-转账中, 2-转账成功, 3-转账失败, 4-订单关闭")
    private Byte state;

    /**
     * 特定渠道发起额外参数
     */
    @ApiModelProperty(value = "特定渠道发起额外参数")
    private String channelExtra;

    /**
     * 渠道订单号
     */
    @ApiModelProperty(value = "渠道订单号")
    private String channelOrderNo;

    /**
     * 渠道支付错误码
     */
    @ApiModelProperty(value = "渠道支付错误码")
    private String errCode;

    /**
     * 渠道支付错误描述
     */
    @ApiModelProperty(value = "渠道支付错误描述")
    private String errMsg;

    /**
     * 商户扩展参数
     */
    @ApiModelProperty(value = "商户扩展参数")
    private String extParam;

    /**
     * 异步通知地址
     */
    @ApiModelProperty(value = "异步通知地址")
    private String notifyUrl;

    /**
     * 转账成功时间
     */
    @ApiModelProperty(value = "转账成功时间")
    private Date successTime;

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
