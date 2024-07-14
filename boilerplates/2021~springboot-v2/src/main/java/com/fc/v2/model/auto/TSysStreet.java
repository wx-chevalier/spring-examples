
package com.fc.v2.model.auto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fc.v2.common.base.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.*;
/**
 * 街道设置对象 t_sys_street
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@TableName("t_sys_street")
public class TSysStreet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 街道代码 */
    private String streetCode;

    /** 父级区代码 */
    private String areaCode;

    /** 街道名称 */
    private String streetName;

    /** 简称 */
    private String shortName;

    /** 经度 */
    private String lng;

    /** 纬度 */
    private String lat;

    /** 排序 */
    private Integer sort;

    /** 状态 */
    private Integer dataState;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setStreetCode(String streetCode)
    {
        this.streetCode = streetCode;
    }

    public String getStreetCode()
    {
        return streetCode;
    }
    public void setAreaCode(String areaCode)
    {
        this.areaCode = areaCode;
    }

    public String getAreaCode()
    {
        return areaCode;
    }
    public void setStreetName(String streetName)
    {
        this.streetName = streetName;
    }

    public String getStreetName()
    {
        return streetName;
    }
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getShortName()
    {
        return shortName;
    }
    public void setLng(String lng)
    {
        this.lng = lng;
    }

    public String getLng()
    {
        return lng;
    }
    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getLat()
    {
        return lat;
    }
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getSort()
    {
        return sort;
    }
    public void setDataState(Integer dataState)
    {
        this.dataState = dataState;
    }

    public Integer getDataState()
    {
        return dataState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("streetCode", getStreetCode())
            .append("areaCode", getAreaCode())
            .append("streetName", getStreetName())
            .append("shortName", getShortName())
            .append("lng", getLng())
            .append("lat", getLat())
            .append("sort", getSort())
            .append("dataState", getDataState())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
