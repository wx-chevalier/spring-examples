package com.cloud.model.user;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 权限标识
 * @author liugh
 *
 */
@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {

	private static final long serialVersionUID = 280565233032255804L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	private String permission;
	private String name;
	@TableField("create_time")
	private Long createTime;
	@TableField("update_time")
	private Long updateTime;

}
