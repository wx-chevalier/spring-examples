
package com.fc.v2.model.auto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import com.baomidou.mybatisplus.annotation.*;
import com.fc.v2.common.base.BaseEntity;

/**
 * 电子邮件对象 t_sys_email
 *
 * @author zhaonz
 * @date 2021-08-06
 */
@TableName("t_sys_email")
public class TSysEmail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 接收人电子邮件 */
    private String receiversEmail;

    /** 邮件标题 */
    private String title;

    /** 内容 */
    private String content;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setReceiversEmail(String receiversEmail)
    {
        this.receiversEmail = receiversEmail;
    }

    public String getReceiversEmail()
    {
        return receiversEmail;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("receiversEmail", getReceiversEmail())
                .append("title", getTitle())
                .append("content", getContent())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}