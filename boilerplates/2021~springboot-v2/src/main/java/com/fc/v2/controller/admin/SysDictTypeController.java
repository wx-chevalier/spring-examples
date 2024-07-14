package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysDictType;
import com.fc.v2.service.ITSysDictTypeService;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典类型Controller
 *
 * @author fuce
 * @ClassName: DictTypeController
 * @date 2019-11-20 22:45
 */
@Api(value = "字典类型表")
@Controller
@RequestMapping("/DictTypeController")
public class SysDictTypeController extends BaseController {

	private final String prefix = "admin/sysDictType";

	@Autowired
	private ITSysDictTypeService tSysDictTypeService;

	/**
	 * 分页list页面
	 *
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@RequiresPermissions("system:dictType:view")
	public String view(ModelMap model) {
		return prefix + "/list";
	}

	/**
	 * 字典类型表集合查询
	 *
	 * @param tSysDictType
	 * @return
	 */
	@Log(title = "字典类型表集合查询", action = "list")
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/list")
	@RequiresPermissions("system:dictType:list")
	@ResponseBody
	public ResultTable list(TSysDictType tSysDictType) {
		QueryWrapper<TSysDictType> queryWrapper = new QueryWrapper<TSysDictType>();
		queryWrapper.like(StringUtils.isNotEmpty(tSysDictType.getDictName()), "dict_name", tSysDictType.getDictName());
		queryWrapper.eq(StringUtils.isNotEmpty(tSysDictType.getStatus()), "status",tSysDictType.getStatus());

		startPage();
		PageInfo<TSysDictType> page = new PageInfo<TSysDictType>(tSysDictTypeService.selectTSysDictTypeList(queryWrapper));
		return pageTable(page.getList(), page.getTotal());
	}

	/**
	 * 新增跳转
	 *
	 * @param modelMap 试图
	 * @return String 跳转连接
	 */
	@ApiOperation(value = "新增跳转", notes = "新增跳转")
	@GetMapping("/add")
	public String add(ModelMap modelMap) {
		return prefix + "/add";
	}

	/**
	 * 新增保存
	 *
	 * @param tSysDictType 字段类型
	 * @return AjaxResult 对象
	 */
	@Log(title = "字典类型表新增", action = "add")
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/add")
	@RequiresPermissions("system:dictType:add")
	@ResponseBody
	public AjaxResult add(TSysDictType tSysDictType) {
		QueryWrapper<TSysDictType> queryWrapper = new QueryWrapper<TSysDictType>();
		queryWrapper.eq(StringUtils.isNotEmpty(tSysDictType.getDictType()), "dict_type", tSysDictType.getDictType());
		List<TSysDictType> tSysDictTypes = tSysDictTypeService.selectTSysDictTypeList(queryWrapper);
		if (tSysDictTypes.size() != 0){
			return error("字典类型重复");
		}
		return toAjax(tSysDictTypeService.insertTSysDictType(tSysDictType));
	}

	/**
	 * 删除字典类型
	 *
	 * @param ids
	 * @return
	 */
	@Log(title = "字典类型表删除", action = "remove")
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/remove")
	@RequiresPermissions("system:dictType:remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(tSysDictTypeService.deleteTSysDictTypeByIds(ids));
	}

	/**
	 * 检查字典类型名字相同
	 *
	 * @param tSysDictType
	 * @return
	 */
	@ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
	@PostMapping("/checkNameUnique")
	@ResponseBody
	public int checkNameUnique(TSysDictType tSysDictType) {
		return tSysDictTypeService.checkNameUnique(tSysDictType) > 0 ? 1 : 0;
	}

	/**
	 * 修改跳转
	 *
	 * @param id
	 * @param mmap
	 * @return
	 */
	@ApiOperation(value = "修改跳转", notes = "修改跳转")
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		mmap.put("TSysDictType", tSysDictTypeService.selectTSysDictTypeById(id));
		return prefix + "/edit";
	}

	/**
	 * 修改保存
	 * 
	 * @param record
	 * @return
	 */
	@Log(title = "字典类型表修改", action = "edit")
	@ApiOperation(value = "修改保存", notes = "修改保存")
	@RequiresPermissions("system:dictType:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TSysDictType record) {
		return toAjax(tSysDictTypeService.updateTSysDictType(record));
	}

	/**
	 * 修改是否启用
	 *
	 * @param record
	 * @return
	 */
	@PutMapping("/updateEnable")
	@ResponseBody
	public AjaxResult updateEnable(@RequestBody TSysDictType record) {
		return toAjax(tSysDictTypeService.updateTSysDictType(record));
	}
}
