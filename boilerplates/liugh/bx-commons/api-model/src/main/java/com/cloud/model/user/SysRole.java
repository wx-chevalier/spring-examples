package com.cloud.model.user;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cloud.model.user.constants.RoleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色
 */
@ApiModel("角色")
@TableName("sys_role")
@Data
public class SysRole implements Serializable {

	private static final long serialVersionUID = -2054359538140713354L;

	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(hidden=true)
	private Long id;

	@ApiModelProperty(value = "角色码")
	private String code;

	@ApiModelProperty(value = "角色名")
	private String name;

	@ApiModelProperty(hidden=true)
	@TableField("create_time")
	private Long createTime;

	@ApiModelProperty(hidden=true)
	@TableField("update_time")
	private Long updateTime;

}
