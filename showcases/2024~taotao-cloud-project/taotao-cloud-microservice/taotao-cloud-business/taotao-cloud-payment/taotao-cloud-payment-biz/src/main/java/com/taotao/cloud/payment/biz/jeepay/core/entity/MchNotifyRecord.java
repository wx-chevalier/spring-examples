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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jeequan.jeepay.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商户通知记录表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
@ApiModel(value = "商户通知记录表", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_mch_notify_record")
public class MchNotifyRecord extends BaseModel implements Serializable {

    //订单类型:1-支付,2-退款, 3-转账
    public static final byte TYPE_PAY_ORDER = 1;
    public static final byte TYPE_REFUND_ORDER = 2;
    public static final byte TYPE_TRANSFER_ORDER = 3;

    //通知状态
    public static final byte STATE_ING = 1;
    public static final byte STATE_SUCCESS = 2;
    public static final byte STATE_FAIL = 3;

    //gw
    public static final LambdaQueryWrapper<MchNotifyRecord> gw(){
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID=1L;

    /**
     * 商户通知记录ID
     */
    @ApiModelProperty(value = "商户通知记录ID")
    @TableId(value = "notify_id", type = IdType.AUTO)
    private Long notifyId;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private String orderId;

    /**
     * 订单类型:1-支付,2-退款
     */
    @ApiModelProperty(value = "订单类型:1-支付,2-退款")
    private Byte orderType;

    /**
     * 商户订单号
     */
    @ApiModelProperty(value = "商户订单号")
    private String mchOrderNo;

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
     * 通知地址
     */
    @ApiModelProperty(value = "通知地址")
    private String notifyUrl;

    /**
     * 通知响应结果
     */
    @ApiModelProperty(value = "通知响应结果")
    private String resResult;

    /**
     * 通知次数
     */
    @ApiModelProperty(value = "通知次数")
    private Integer notifyCount;

    /**
     * 最大通知次数, 默认6次
     */
    @ApiModelProperty(value = "最大通知次数, 默认6次")
    private Integer notifyCountLimit;

    /**
     * 通知状态,1-通知中,2-通知成功,3-通知失败
     */
    @ApiModelProperty(value = "通知状态,1-通知中,2-通知成功,3-通知失败")
    private Byte state;

    /**
     * 最后一次通知时间
     */
    @ApiModelProperty(value = "最后一次通知时间")
    private Date lastNotifyTime;

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
