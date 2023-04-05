package com.cloud.user.service;

import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.model.user.SysPermission;

public interface SysPermissionService extends IService<SysPermission> {

	/**
	 * 根绝角色ids获取权限集合
	 * 
	 * @param roleIds
	 * @return
	 */
	Set<SysPermission> findByRoleIds(Set<Long> roleIds);

	void saveSysPermission(SysPermission sysPermission);

	void delete(Long id);

	Page<SysPermission> findPermissionsByPage(Page<SysPermission> sysPermissionPage, Map<String, Object> params);
}
