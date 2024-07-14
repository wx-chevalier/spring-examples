
package com.fc.v2.model.auto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fc.v2.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.*;
/**
 * 用户对象 t_sys_user
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@TableName("t_sys_user")
@ApiModel(value = "TSysUser", description = "用户表")
public class TSysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 用户账号 */
    @ApiModelProperty(value = "用户账号")
    private String username;

    /** 用户密码 */
    @ApiModelProperty(value = "用户密码")
    private String password;

    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /** 用户类型 */
    @ApiModelProperty(value = "用户类型")
    private String userType;

    /** 用户邮箱 */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /** 手机号码 */
    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    /** 用户性别 0=男,1=女,2=未知*/
    @ApiModelProperty(value = "用户性别")
    private String sex;

    /** 用户头像 */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /** 部门id */
    @ApiModelProperty(value = "部门id")
    private Long depId;

    /** 岗位id */
    @ApiModelProperty(value = "岗位id")
    private Long posId;

    /** 部门名称 */
    @TableField(exist = false)
    private String depName;

    /** 岗位名称 */
    @TableField(exist = false)
    private String posName;

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }
    public void setDepId(Long depId)
    {
        this.depId = depId;
    }

    public Long getDepId()
    {
        return depId;
    }
    public void setPosId(Long posId)
    {
        this.posId = posId;
    }

    public Long getPosId()
    {
        return posId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("username", getUsername())
            .append("password", getPassword())
            .append("nickname", getNickname())
            .append("userType", getUserType())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("depId", getDepId())
            .append("posId", getPosId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }


}
