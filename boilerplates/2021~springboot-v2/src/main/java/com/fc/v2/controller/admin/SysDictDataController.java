package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysDictData;
import com.fc.v2.service.ITSysDictDataService;
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
 * 字典表Controller
 *
 * @author fuce
 * @ClassName: DictDataController
 * @date 2019-11-20 22:46
 */
@Api(value = "字典数据表")
@Controller
@RequestMapping("/DictDataController")
public class SysDictDataController extends BaseController {

	private final String prefix = "admin/sysDictData";

	@Autowired
	private ITSysDictDataService tSysDictDataService;

	/**
	 * 分页list页面
	 *
	 * @param model
	 * @param tSysDictData
	 * @return
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@RequiresPermissions("system:dictData:view")
	public String view(ModelMap model, TSysDictData tSysDictData) {
		model.addAttribute("dictType", tSysDictData.getDictType());
		return prefix + "/list";
	}

	/**
	 * 字典数据表集合查询
	 *
	 * @param tSysDictData
	 * @return
	 */
	@Log(title = "字典数据表集合查询", action = "list")
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/list")
	@RequiresPermissions("system:dictData:list")
	@ResponseBody
	public ResultTable list(TSysDictData tSysDictData) {
		QueryWrapper<TSysDictData> queryWrapper = new QueryWrapper<TSysDictData>();
		queryWrapper.eq(StringUtils.isNotEmpty(tSysDictData.getDictType()),"dict_type", tSysDictData.getDictType());
		queryWrapper.like(StringUtils.isNotEmpty(tSysDictData.getDictValue()),"dict_value", tSysDictData.getDictValue());
		queryWrapper.eq(StringUtils.isNotEmpty(tSysDictData.getStatus()),"status", tSysDictData.getStatus());

		startPage();
		PageInfo<TSysDictData> page = new PageInfo<TSysDictData>(tSysDictDataService.selectTSysDictDataList(queryWrapper));
		return pageTable(page.getList(), page.getTotal());
	}

	/**
	 * 新增跳转
	 *
	 * @param modelMap
	 * @param tSysDictData
	 * @return
	 */
	@ApiOperation(value = "新增跳转", notes = "新增跳转")
	@GetMapping("/add")
	public String add(ModelMap modelMap, TSysDictData tSysDictData) {
		modelMap.addAttribute("dictType", tSysDictData.getDictType());
		return prefix + "/add";
	}

	/**
	 * 新增保存
	 *
	 * @param tSysDictData
	 * @return
	 */
	@Log(title = "字典数据表新增", action = "add")
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/add")
	@RequiresPermissions("system:dictData:add")
	@ResponseBody
	public AjaxResult add(TSysDictData tSysDictData) {
		QueryWrapper<TSysDictData> queryWrapper = new QueryWrapper<TSysDictData>();
		queryWrapper.like(StringUtils.isNotEmpty(tSysDictData.getDictValue()),"dict_value", tSysDictData.getDictValue());
		List<TSysDictData> sysDictDatas = tSysDictDataService.selectTSysDictDataList(queryWrapper);
		if (sysDictDatas.size() != 0){
			return error("字典键值重复");
		}
		return toAjax(tSysDictDataService.insertTSysDictData(tSysDictData));
	}

	/**
	 * 字典数据删除
	 *
	 * @param ids
	 * @return
	 */
	@Log(title = "字典数据表删除", action = "remove")
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/remove")
	@RequiresPermissions("system:dictData:remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(tSysDictDataService.deleteTSysDictDataByIds(ids));
	}

	/**
	 * 检查
	 *
	 * @param tSysDictData
	 * @return
	 */
	@ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
	@PostMapping("/checkNameUnique")
	@ResponseBody
	public int checkNameUnique(TSysDictData tSysDictData) {
		return tSysDictDataService.checkNameUnique(tSysDictData) > 0 ? 1 : 0;
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
		TSysDictData sysDictData = tSysDictDataService.selectTSysDictDataById(id);
		mmap.put("TSysDictData", sysDictData);
		return prefix + "/edit";
	}

	/**
	 * 修改保存
	 * 
	 * @param record
	 * @return
	 */
	@Log(title = "字典数据表修改", action = "edit")
	@ApiOperation(value = "修改保存", notes = "修改保存")
	@RequiresPermissions("system:dictData:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TSysDictData record) {
		return toAjax(tSysDictDataService.updateTSysDictData(record));
	}

	/**
	 * 修改是否默认
	 *
	 * @param record
	 * @return
	 */
	@PutMapping("/updateDefault")
	@ResponseBody
	public AjaxResult updateDefault(@RequestBody TSysDictData record) {
		return toAjax(tSysDictDataService.updateTSysDictData(record));
	}

	/**
	 * 修改是否启用
	 *
	 * @param record
	 * @return
	 */
	@PutMapping("/updateEnable")
	@ResponseBody
	public AjaxResult updateEnable(@RequestBody TSysDictData record) {
		return toAjax(tSysDictDataService.updateTSysDictData(record));
	}
}
