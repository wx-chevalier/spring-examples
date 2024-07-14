package com.fc.v2.model.custom;

import com.fc.v2.model.auto.TSysRole;

/**
 * 角色自定义数据
 * @author fuce 
 * @date: 2018年9月8日 上午12:18:59
 */
public class RoleVo extends TSysRole{
	private static final long serialVersionUID = 1L;
	private boolean ischeck;//判断是否又这个权限
	
	public boolean isIscheck() {
		return ischeck;
	}
	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}
	
	public RoleVo() {
		super();
	}
	public RoleVo(Long id, String name,Boolean ischeck) {
		super(id, name);
		this.ischeck=ischeck;
	}
	
}
