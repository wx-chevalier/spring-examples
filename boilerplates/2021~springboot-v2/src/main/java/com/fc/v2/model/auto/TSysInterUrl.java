
package com.fc.v2.model.auto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.*;
import com.fc.v2.common.base.BaseEntity;

/**
 * 拦截url对象 t_sys_inter_url
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@TableName("t_sys_inter_url")
@ApiModel(value="TSysInterUrl", description="拦截url表")
public class TSysInterUrl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 拦截名称 */
    @ApiModelProperty(value = "拦截名称")
    private String interName;

    /** 拦截url */
    @ApiModelProperty(value = "拦截url")
    private String url;

    /** 类型 */
    @ApiModelProperty(value = "类型")
    private Integer type;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setInterName(String interName)
    {
        this.interName = interName;
    }

    public String getInterName()
    {
        return interName;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("interName", getInterName())
            .append("url", getUrl())
            .append("type", getType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
