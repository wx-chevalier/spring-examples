package com.cloud.user.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.model.user.SysPermission;
import com.cloud.model.user.SysRole;

import java.util.Map;
import java.util.Set;

public interface SysRoleService extends IService<SysRole> {

	void saveSysRole(SysRole sysRole);

	SysRole deleteRole(Long id);

	void setPermissionToRole(Long id, Set<Long> permissionIds);

	Set<SysPermission> findPermissionsByRoleId(Long roleId);

	Page<SysRole> findRolesByPage(Page<SysRole> sysRolePage, String  name);
}
