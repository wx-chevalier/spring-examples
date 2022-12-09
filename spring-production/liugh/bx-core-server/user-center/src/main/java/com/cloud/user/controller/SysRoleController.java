package com.cloud.user.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.utils.ResultHelper;
import com.cloud.model.user.SysRole;
import com.cloud.user.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Api(tags = "角色模块")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 管理后台添加角色
	 *
	 * @param sysRole
	 */
//	@LogAnnotation(module = LogModule.ADD_ROLE)
	@PreAuthorize("hasAuthority('back:role:save')")
	@PostMapping("/roles")
	@ApiOperation(value = "添加,修改角色")
	public ResultHelper save(@RequestBody SysRole sysRole) {
		if (StringUtils.isBlank(sysRole.getCode())) {
			throw new IllegalArgumentException("角色code不能为空");
		}
		if (StringUtils.isBlank(sysRole.getName())) {
			sysRole.setName(sysRole.getCode());
		}

		sysRoleService.saveSysRole(sysRole);
		return ResultHelper.succeed(sysRole);
	}

	/**
	 * 管理后台删除角色
	 *
	 * @param id
	 */
	@ApiOperation(value = "删除角色(连同用户和权限一起删了)")
//	@LogAnnotation(module = LogModule.DELETE_ROLE)
	@PreAuthorize("hasAuthority('back:role:delete')")
	@DeleteMapping("/roles/{id}")
	public ResultHelper deleteRole(@PathVariable Long id) {
		return ResultHelper.succeed(sysRoleService.deleteRole(id));
	}


	/**
	 * 管理后台给角色分配权限
	 *
	 * @param id            角色id
	 * @param permissionIds 权限ids
	 */
	@ApiOperation(value = "管理后台给角色分配权限")
//	@LogAnnotation(module = LogModule.SET_PERMISSION)
	@PreAuthorize("hasAuthority('back:role:permission:set')")
	@PostMapping("/roles/{id}/permissions")
	public void setPermissionToRole(@PathVariable Long id, @RequestBody Set<Long> permissionIds) {
		sysRoleService.setPermissionToRole(id, permissionIds);
	}

	/**
	 * 获取角色的权限
	 *
	 * @param id
	 */
	@ApiOperation(value = "根据角色id获取角色的权限")
	@PreAuthorize("hasAnyAuthority('back:role:permission:set','role:permission:byroleid')")
	@GetMapping("/roles/{id}/permissions")
	public ResultHelper findPermissionsByRoleId(@PathVariable Long id) {
		return ResultHelper.succeed(sysRoleService.findPermissionsByRoleId(id));
	}

	@ApiOperation(value = "获取单个角色信息")
	@PreAuthorize("hasAuthority('back:role:query')")
	@GetMapping("/roles/{id}")
	public ResultHelper findById(@PathVariable Long id) {
		return ResultHelper.succeed(sysRoleService.selectById(id));
	}

	/**
	 * 搜索角色
	 */
	@ApiOperation(value = "分页查询角色")
	@PreAuthorize("hasAuthority('back:role:query')")
	@GetMapping("/roles")
	public ResultHelper findRoles(

			@RequestParam(name = "current", defaultValue = "1", required = false) Integer current,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(name = "name", defaultValue = "", required = false) String name) {
		return ResultHelper.succeed(sysRoleService.findRolesByPage(new Page<SysRole>(current,size),name));
	}

}
