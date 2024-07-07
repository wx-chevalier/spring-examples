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
 * 商户信息表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
@ApiModel(value = "商户信息表", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_mch_info")
public class MchInfo extends BaseModel implements Serializable {

    //gw
    public static final LambdaQueryWrapper<MchInfo> gw(){
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID=1L;

    public static final byte TYPE_NORMAL = 1; //商户类型： 1-普通商户
    public static final byte TYPE_ISVSUB = 2; //商户类型： 2-特约商户


    /**
     * 商户号
     */
    @ApiModelProperty(value = "商户号")
    @TableId(value = "mch_no", type = IdType.INPUT)
    private String mchNo;

    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户名称")
    private String mchName;

    /**
     * 商户简称
     */
    @ApiModelProperty(value = "商户简称")
    private String mchShortName;

    /**
     * 类型: 1-普通商户, 2-特约商户(服务商模式)
     */
    @ApiModelProperty(value = "类型: 1-普通商户, 2-特约商户(服务商模式)")
    private Byte type;

    /**
     * 服务商号
     */
    @ApiModelProperty(value = "服务商号")
    private String isvNo;

    /**
     * 联系人姓名
     */
    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    /**
     * 联系人手机号
     */
    @ApiModelProperty(value = "联系人手机号")
    private String contactTel;

    /**
     * 联系人邮箱
     */
    @ApiModelProperty(value = "联系人邮箱")
    private String contactEmail;

    /**
     * 商户状态: 0-停用, 1-正常
     */
    @ApiModelProperty(value = "商户状态: 0-停用, 1-正常")
    private Byte state;

    /**
     * 商户备注
     */
    @ApiModelProperty(value = "商户备注")
    private String remark;

    /**
     * 初始用户ID（创建商户时，允许商户登录的用户）
     */
    @ApiModelProperty(value = "初始用户ID（创建商户时，允许商户登录的用户）")
    private Long initUserId;

    /**
     * 创建者用户ID
     */
    @ApiModelProperty(value = "创建者用户ID")
    private Long createdUid;

    /**
     * 创建者姓名
     */
    @ApiModelProperty(value = "创建者姓名")
    private String createdBy;

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
