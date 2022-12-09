package com.cloud.model.user;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@TableName("app_user")
@Data
@ApiModel("用户对象")
public class AppUser extends Model<AppUser> implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	@ApiModelProperty(hidden=true)
	private String id;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "密码")
	private String password;

	@TableField(exist = false)
	@ApiModelProperty(value = "重复密码")
	private String rePassword;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "头像url")
	@TableField("head_img_url")
	private String headImgUrl;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "性别 1:男 2女")
	private Integer sex;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态（1有效,0无效）")
	private Boolean enabled;
	@ApiModelProperty(value = "类型（哪个业务的用户）")
	private String type;
	@ApiModelProperty(hidden=true)
	@TableField("create_time")
	private Long createTime;
	@ApiModelProperty(hidden=true)
	@TableField("update_time")
	private Long updateTime;

	@ApiModelProperty(value = "角色")
	@TableField(exist = false)
	private String role;

	@TableField("organization_id")
	private Integer organizationId;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
