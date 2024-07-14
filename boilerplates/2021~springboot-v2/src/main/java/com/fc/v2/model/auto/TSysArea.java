
package com.fc.v2.model.auto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fc.v2.common.base.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotation.*;
/**
 * 地区设置对象 t_sys_area
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@TableName("t_sys_area")
public class TSysArea extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 区代码 */
    private String areaCode;

    /** 父级市代码 */
    private String cityCode;

    /** 市名称 */
    private String areaName;

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
    public void setAreaCode(String areaCode)
    {
        this.areaCode = areaCode;
    }

    public String getAreaCode()
    {
        return areaCode;
    }
    public void setCityCode(String cityCode)
    {
        this.cityCode = cityCode;
    }

    public String getCityCode()
    {
        return cityCode;
    }
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public String getAreaName()
    {
        return areaName;
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
            .append("areaCode", getAreaCode())
            .append("cityCode", getCityCode())
            .append("areaName", getAreaName())
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
