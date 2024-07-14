
package com.fc.v2.model.auto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fc.v2.util.DateUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import com.baomidou.mybatisplus.annotation.*;
import com.fc.v2.common.base.BaseEntity;

/**
 * 日志记录对象 t_sys_oper_log
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@TableName("t_sys_oper_log")
public class TSysOperLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 标题 */
    private String title;

    /** 方法 */
    private String method;

    /** 操作人 */
    private String operName;

    /** url */
    private String operUrl;

    /** 参数 */
    private String operParam;

    /**  */
    private String errorMsg;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date operTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getMethod()
    {
        return method;
    }
    public void setOperName(String operName)
    {
        this.operName = operName;
    }

    public String getOperName()
    {
        return operName;
    }
    public void setOperUrl(String operUrl)
    {
        this.operUrl = operUrl;
    }

    public String getOperUrl()
    {
        return operUrl;
    }
    public void setOperParam(String operParam)
    {
        this.operParam = operParam;
    }

    public String getOperParam()
    {
        return operParam;
    }
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }
    public void setOperTime(Date operTime)
    {
        this.operTime = operTime;
    }

    public Date getOperTime()
    {
        return operTime;
    }

    /**
     * 格式化时间
     *
     * @return
     */
    public String getdate(){
        return  DateUtils.dateTime(this.operTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("method", getMethod())
            .append("operName", getOperName())
            .append("operUrl", getOperUrl())
            .append("operParam", getOperParam())
            .append("errorMsg", getErrorMsg())
            .append("operTime", getOperTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
