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

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
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
 * 支付接口定义表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2021-04-27
 */
@ApiModel(value = "支付接口定义表", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "t_pay_interface_define", autoResultMap = true)
public class PayInterfaceDefine extends BaseModel implements Serializable {

    public static final LambdaQueryWrapper<PayInterfaceDefine> gw(){
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID=1L;

    /**
     * 接口代码 全小写  wxpay alipay 
     */
    @ApiModelProperty(value = "接口代码 全小写  wxpay alipay")
    @TableId
    private String ifCode;

    /**
     * 接口名称
     */
    @ApiModelProperty(value = "接口名称")
    private String ifName;

    /**
     * 是否支持普通商户模式: 0-不支持, 1-支持
     */
    @ApiModelProperty(value = "是否支持普通商户模式: 0-不支持, 1-支持")
    private Byte isMchMode;

    /**
     * 是否支持服务商子商户模式: 0-不支持, 1-支持
     */
    @ApiModelProperty(value = "是否支持服务商子商户模式: 0-不支持, 1-支持")
    private Byte isIsvMode;

    /**
     * 支付参数配置页面类型:1-JSON渲染,2-自定义
     */
    @ApiModelProperty(value = "支付参数配置页面类型:1-JSON渲染,2-自定义")
    private Byte configPageType;

    /**
     * ISV接口配置定义描述,json字符串
     */
    @ApiModelProperty(value = "ISV接口配置定义描述,json字符串")
    private String isvParams;

    /**
     * 特约商户接口配置定义描述,json字符串
     */
    @ApiModelProperty(value = "特约商户接口配置定义描述,json字符串")
    private String isvsubMchParams;

    /**
     * 普通商户接口配置定义描述,json字符串
     */
    @ApiModelProperty(value = "普通商户接口配置定义描述,json字符串")
    private String normalMchParams;

    /**
     * 支持的支付方式 ["wxpay_jsapi", "wxpay_bar"]
     */
    @ApiModelProperty(value = "支持的支付方式")
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONArray wayCodes;

    /**
     * 页面展示：卡片-图标
     */
    @ApiModelProperty(value = "页面展示：卡片-图标")
    private String icon;

    /**
     * 页面展示：卡片-背景色
     */
    @ApiModelProperty(value = "页面展示：卡片-背景色")
    private String bgColor;

    /**
     * 状态: 0-停用, 1-启用
     */
    @ApiModelProperty(value = "状态: 0-停用, 1-启用")
    private Byte state;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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
