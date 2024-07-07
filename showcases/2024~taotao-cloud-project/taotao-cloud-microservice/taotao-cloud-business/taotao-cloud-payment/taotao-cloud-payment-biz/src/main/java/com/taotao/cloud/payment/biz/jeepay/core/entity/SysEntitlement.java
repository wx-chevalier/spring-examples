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
 * 系统权限表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2021-04-23
 */
@ApiModel(value = "系统权限表", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_entitlement")
public class SysEntitlement extends BaseModel {

    //gw
    public static final LambdaQueryWrapper<SysEntitlement> gw(){
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID=1L;

    /**
     * 权限ID[ENT_功能模块_子模块_操作], eg: ENT_ROLE_LIST_ADD
     */
    @ApiModelProperty(value = "权限ID[ENT_功能模块_子模块_操作], eg: ENT_ROLE_LIST_ADD")
    @TableId
    private String entId;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String entName;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    /**
     * 菜单uri/路由地址
     */
    @ApiModelProperty(value = "菜单uri/路由地址")
    private String menuUri;

    /**
     * 组件Name（前后端分离使用）
     */
    @ApiModelProperty(value = "组件Name（前后端分离使用）")
    private String componentName;

    /**
     * 权限类型 ML-左侧显示菜单, MO-其他菜单, PB-页面/按钮
     */
    @ApiModelProperty(value = "权限类型 ML-左侧显示菜单, MO-其他菜单, PB-页面/按钮")
    private String entType;

    /**
     * 快速开始菜单 0-否, 1-是
     */
    @ApiModelProperty(value = "快速开始菜单 0-否, 1-是")
    private Byte quickJump;

    /**
     * 状态 0-停用, 1-启用
     */
    @ApiModelProperty(value = "状态 0-停用, 1-启用")
    private Byte state;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private String pid;

    /**
     * 排序字段, 规则：正序
     */
    @ApiModelProperty(value = "排序字段, 规则：正序")
    private Integer entSort;

    /**
     * 所属系统： MGR-运营平台, MCH-商户中心
     */
    @ApiModelProperty(value = "所属系统： MGR-运营平台, MCH-商户中心")
    private String sysType;

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
