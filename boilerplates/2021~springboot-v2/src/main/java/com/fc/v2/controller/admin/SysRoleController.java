package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysRole;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 角色Controller
 *
 * @author fuce
 * @date: 2018年9月2日 下午8:08:21
 */
@Api(value = "用户角色")
@Controller
@RequestMapping("/RoleController")
public class SysRoleController extends BaseController {

	/**
	 * 跳转页面参数
	 */
	private final String prefix = "admin/sysRole";

	/**
	 * 展示页面
	 *
	 * @param model
	 * @return
	 * @author fuce
	 * @Date 2019年11月11日 下午4:01:58
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@RequiresPermissions("system:role:view")
	public String view(ModelMap model) {
		return prefix + "/list";
	}

	/**
	 * 角色列表
	 *
	 * @param tSysRole
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/list")
	@RequiresPermissions("system:role:list")
	@ResponseBody
	public ResultTable list(TSysRole tSysRole) {
		QueryWrapper<TSysRole> queryWrapper = new QueryWrapper<TSysRole>();
		queryWrapper.like(StringUtils.isNotEmpty(tSysRole.getName()), "name", tSysRole.getName());

		startPage();
		PageInfo<TSysRole> page = new PageInfo<TSysRole>(sysRoleService.selectTSysRoleList(queryWrapper));
		return treeTable(page.getList());
	}

	/**
	 * 新增角色
	 * 
	 * @return
	 */
	@ApiOperation(value = "新增跳转", notes = "新增跳转")
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 角色添加
	 *
	 * @param role
	 * @return
	 */
	@Log(title = "角色添加", action = "add")
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/add")
	@RequiresPermissions("system:role:add")
	@ResponseBody
	public AjaxResult add(@RequestBody TSysRole role) {
		if (sysRoleService.checkNameUnique(role) > 0){
			return error("角色名重复");
		}
		return toAjax(sysRoleService.insertTSysRole(role));
	}

	/**
	 * 删除角色
	 *
	 * @param ids
	 * @return
	 */
	@Log(title = "删除角色", action = "remove")
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/remove")
	@RequiresPermissions("system:role:remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(sysRoleService.deleteTSysRoleByIds(ids));
	}

	/**
	 * 修改角色
	 *
	 * @param id
	 * @param mmap
	 * @return
	 */
	@ApiOperation(value = "修改跳转", notes = "修改跳转")
	@GetMapping("/edit/{roleId}")
	public String edit(@PathVariable("roleId") Long id, ModelMap mmap) {
		mmap.put("sysRole", sysRoleService.selectTSysRoleById(id));
		return prefix + "/edit";
	}

	/**
	 * 修改保存角色
	 * 
	 * @param tsysRole
	 * @return
	 */
	@Log(title = "修改保存角色", action = "editSave")
	@ApiOperation(value = "修改保存", notes = "修改保存")
	@RequiresPermissions("system:role:edit")
	@PutMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody TSysRole tsysRole) {
		return toAjax(sysRoleService.updateTSysRole(tsysRole));
	}
}
