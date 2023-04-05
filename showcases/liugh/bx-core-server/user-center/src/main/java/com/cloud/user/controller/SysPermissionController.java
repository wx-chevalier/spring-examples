package com.cloud.user.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.utils.ResultHelper;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.SysPermission;
import com.cloud.user.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@Slf4j
@ApiIgnore
public class SysPermissionController {

	@Autowired
	private SysPermissionService sysPermissionService;

	/**
	 * 管理后台添加权限
	 *
	 * @param sysPermission
	 * @return
	 */
	@LogAnnotation(module = LogModule.ADD_PERMISSION)
	@PreAuthorize("hasAuthority('back:permission:save')")
	@PostMapping("/permissions")
	public ResultHelper save(@RequestBody SysPermission sysPermission) {
		if (StringUtils.isBlank(sysPermission.getPermission())) {
			throw new IllegalArgumentException("权限标识不能为空");
		}
		if (StringUtils.isBlank(sysPermission.getName())) {
			throw new IllegalArgumentException("权限名不能为空");
		}
		sysPermissionService.saveSysPermission(sysPermission);
		return  ResultHelper.succeed(sysPermission);
	}

	/**
	 * 管理后台修改权限
	 *
	 * @param sysPermission
	 */
	@LogAnnotation(module = LogModule.UPDATE_PERMISSION)
	@PreAuthorize("hasAuthority('back:permission:update')")
	@PutMapping("/permissions")
	public ResultHelper update(@RequestBody SysPermission sysPermission) {
		if (StringUtils.isBlank(sysPermission.getName())) {
			throw new IllegalArgumentException("权限名不能为空");
		}
		sysPermission.setUpdateTime(System.currentTimeMillis());
		log.info("修改权限标识：{}", sysPermission);
		sysPermissionService.updateById(sysPermission);
		return  ResultHelper.succeed(sysPermission);
	}

	/**
	 * 删除权限标识
	 *
	 * @param id
	 */
	@LogAnnotation(module = LogModule.DELETE_PERMISSION)
	@PreAuthorize("hasAuthority('back:permission:delete')")
	@DeleteMapping("/permissions/{id}")
	public void delete(@PathVariable Long id) {
		sysPermissionService.delete(id);
	}

	/**
	 * 查询所有的权限标识
	 */
	@PreAuthorize("hasAuthority('back:permission:query')")
	@GetMapping("/permissions")
	public ResultHelper findPermissions(
			@RequestParam(name = "current", defaultValue = "1", required = false) Integer current,
			@RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam Map<String, Object> params) {
		return  ResultHelper.succeed(sysPermissionService.findPermissionsByPage(new Page<SysPermission>(current,size),params));
	}
}
