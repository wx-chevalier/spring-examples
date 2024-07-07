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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统用户认证表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2021-04-23
 */
@ApiModel(value = "系统用户认证表", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_user_auth")
public class SysUserAuth implements Serializable {

    //gw
    public static final LambdaQueryWrapper<SysUserAuth> gw(){
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value = "auth_id", type = IdType.AUTO)
    private Long authId;

    /**
     * user_id
     */
    @ApiModelProperty(value = "user_id")
    private Long userId;

    /**
     * 登录类型  1-昵称 2-手机号 3-邮箱  10-微信  11-QQ 12-支付宝 13-微博
     */
    @ApiModelProperty(value = "登录类型  1-昵称 2-手机号 3-邮箱  10-微信  11-QQ 12-支付宝 13-微博")
    private Byte identityType;

    /**
     * 认证标识 ( 用户名 | open_id )
     */
    @ApiModelProperty(value = "认证标识 ( 用户名 | open_id )")
    private String identifier;

    /**
     * 密码凭证
     */
    @ApiModelProperty(value = "密码凭证")
    private String credential;

    /**
     * salt
     */
    @ApiModelProperty(value = "salt")
    private String salt;

    /**
     * 所属系统： MGR-运营平台, MCH-商户中心
     */
    @ApiModelProperty(value = "所属系统： MGR-运营平台, MCH-商户中心")
    private String sysType;



}
