
package com.fc.v2.model.auto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fc.v2.common.base.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.*;
/**
 * 用户角色中间对象 t_sys_role_user
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@TableName("t_sys_role_user")
public class TSysRoleUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 用户id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sysUserId;

    /** 角色id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sysRoleId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void seTSysUserId(Long sysUserId)
    {
        this.sysUserId = sysUserId;
    }

    public Long geTSysUserId()
    {
        return sysUserId;
    }
    public void setSysRoleId(Long sysRoleId)
    {
        this.sysRoleId = sysRoleId;
    }

    public Long getSysRoleId()
    {
        return sysRoleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sysUserId", geTSysUserId())
            .append("sysRoleId", getSysRoleId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
