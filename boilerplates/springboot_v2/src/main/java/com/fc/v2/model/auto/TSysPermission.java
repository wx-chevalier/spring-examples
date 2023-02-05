
package com.fc.v2.model.auto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fc.v2.common.base.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.*;
/**
 * 权限对象 t_sys_permission
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@TableName("t_sys_permission")
public class TSysPermission extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 权限名称 */
    private String name;

    /** 权限描述 */
    private String descripion;

    /** 授权链接 */
    private String url;

    /** 是否跳转 0 不跳转 1跳转 */
    private Integer isBlank;

    /** 父节点id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    /** 权限标识 */
    private String perms;

    /** 类型   0：目录   1：菜单   2：按钮 */
    private Integer type;

    /** 菜单图标 */
    private String icon;

    /** 排序 */
    private Integer orderNum;

    /** 是否可见 */
    private Integer visible;

    @TableField(exist = false)
    private Integer childCount;

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setDescripion(String descripion)
    {
        this.descripion = descripion;
    }

    public String getDescripion()
    {
        return descripion;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setIsBlank(Integer isBlank)
    {
        this.isBlank = isBlank;
    }

    public Integer getIsBlank()
    {
        return isBlank;
    }
    public void setPid(Long pid)
    {
        this.pid = pid;
    }

    public Long getPid()
    {
        return pid;
    }
    public void setPerms(String perms)
    {
        this.perms = perms;
    }

    public String getPerms()
    {
        return perms;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getIcon()
    {
        return icon;
    }
    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }
    public void setVisible(Integer visible)
    {
        this.visible = visible;
    }

    public Integer getVisible()
    {
        return visible;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("descripion", getDescripion())
            .append("url", getUrl())
            .append("isBlank", getIsBlank())
            .append("pid", getPid())
            .append("perms", getPerms())
            .append("type", getType())
            .append("icon", getIcon())
            .append("orderNum", getOrderNum())
            .append("visible", getVisible())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
