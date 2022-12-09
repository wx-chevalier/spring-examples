package com.cloud.model.user;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 机构表
 * </p>
 *
 * @author liugh123
 * @since 2019-07-04
 */
@TableName("tb_organization")
@Data
@ApiModel(value="Organization对象", description="机构表")
public class Organization {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "机构名称")
    @TableField("organization_name")
    private String organizationName;

    @ApiModelProperty(value = "机构标识")
    private String img;

    @ApiModelProperty(value = "联系人")
    private String manager;

    @ApiModelProperty(value = "联系电话")
    private String telephone;

    @ApiModelProperty(value = "删除状态 0:正常 1:已删除")
    @TableField("delete_status")
    private Integer deleteStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Long createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Long updateTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

}
