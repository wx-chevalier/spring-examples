/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysPublicParam;
import com.pig4cloud.pig.admin.service.SysPublicParamService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 公共参数
 *
 * @author Lucky
 * @date 2019-04-29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/param")
@Tag(name = "公共参数配置")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class PublicParamController {

	private final SysPublicParamService sysPublicParamService;

	/**
	 * 通过key查询公共参数值
	 * @param publicKey
	 * @return
	 */
	@Inner(value = false)
	@Operation(summary = "查询公共参数值", description = "根据key查询公共参数值")
	@GetMapping("/publicValue/{publicKey}")
	public R publicKey(@PathVariable("publicKey") String publicKey) {
		return R.ok(sysPublicParamService.getSysPublicParamKeyToValue(publicKey));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysPublicParam 公共参数
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	public R getSysPublicParamPage(Page page, SysPublicParam sysPublicParam) {
		return R.ok(sysPublicParamService.page(page, Wrappers.query(sysPublicParam)));
	}

	/**
	 * 通过id查询公共参数
	 * @param publicId id
	 * @return R
	 */
	@Operation(summary = "通过id查询公共参数", description = "通过id查询公共参数")
	@GetMapping("/{publicId}")
	public R getById(@PathVariable("publicId") Long publicId) {
		return R.ok(sysPublicParamService.getById(publicId));
	}

	/**
	 * 新增公共参数
	 * @param sysPublicParam 公共参数
	 * @return R
	 */
	@Operation(summary = "新增公共参数", description = "新增公共参数")
	@SysLog("新增公共参数")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_publicparam_add')")
	public R save(@RequestBody SysPublicParam sysPublicParam) {
		return R.ok(sysPublicParamService.save(sysPublicParam));
	}

	/**
	 * 修改公共参数
	 * @param sysPublicParam 公共参数
	 * @return R
	 */
	@Operation(summary = "修改公共参数", description = "修改公共参数")
	@SysLog("修改公共参数")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_publicparam_edit')")
	public R updateById(@RequestBody SysPublicParam sysPublicParam) {
		return sysPublicParamService.updateParam(sysPublicParam);
	}

	/**
	 * 通过id删除公共参数
	 * @param publicId id
	 * @return R
	 */
	@Operation(summary = "删除公共参数", description = "删除公共参数")
	@SysLog("删除公共参数")
	@DeleteMapping("/{publicId}")
	@PreAuthorize("@pms.hasPermission('sys_publicparam_del')")
	public R removeById(@PathVariable Long publicId) {
		return sysPublicParamService.removeParam(publicId);
	}

	/**
	 * 同步参数
	 * @return R
	 */
	@SysLog("同步参数")
	@PutMapping("/sync")
	public R sync() {
		return sysPublicParamService.syncParamCache();
	}

}
