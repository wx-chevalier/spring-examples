package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysArea;
import com.fc.v2.model.auto.TSysCity;
import com.fc.v2.service.ITSysAreaService;
import com.fc.v2.service.ITSysCityService;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 地区Controller
 *
 * @author fuce
 * @ClassName: SysAreaController
 * @date 2019-11-20 22:34
 */
@Api(value = "地区设置")
@Controller
@RequestMapping("/SysAreaController")
public class SysAreaController extends BaseController {

    private String prefix = "admin/sysProvince/sysArea";

    @Autowired
    private ITSysAreaService sysAreaService;

    @Autowired
    private ITSysCityService sysCityService;

    /**
     * 地区页面展示
     *
     * @param model
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:05:04
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("gen:sysArea:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * list集合
     *
     * @param tSysArea
     * @return
     */
    @Log(title = "地区设置集合查询", action = "list")
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/list")
    @RequiresPermissions("gen:sysArea:list")
    @ResponseBody
    public ResultTable list(TSysArea tSysArea) {
        QueryWrapper<TSysArea> queryWrapper = new QueryWrapper<TSysArea>();
        queryWrapper.eq(StringUtils.isNotEmpty(tSysArea.getAreaCode()), "area_code", tSysArea.getAreaCode());
        queryWrapper.like(StringUtils.isNotEmpty(tSysArea.getAreaName()), "area_name", tSysArea.getAreaName());

        startPage();
        PageInfo<TSysArea> page = new PageInfo<TSysArea>(sysAreaService.selectTSysAreaList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    /**
     * 新增跳转
     *
     * @param modelMap
     * @return
     */
    @ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        List<TSysCity> sysCities = sysCityService.selectTSysCityList(new QueryWrapper<TSysCity>());
        modelMap.put("sysCities", sysCities);
        return prefix + "/add";
    }

    /**
     * 新增保存
     *
     * @param sysArea
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:04:41
     */
    @Log(title = "地区设置新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("gen:sysArea:add")
    @ResponseBody
    public AjaxResult add(TSysArea sysArea) {
        return toAjax(sysAreaService.insertTSysArea(sysArea));
    }

    /**
     * 地区设置删除
     *
     * @param ids
     * @return
     */
    @Log(title = "地区设置删除", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("gen:sysArea:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysAreaService.deleteTSysAreaByIds(ids));
    }

    /**
     * 检查
     *
     * @param sysArea
     * @return
     */
    @ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysArea sysArea) {
        return sysAreaService.checkNameUnique(sysArea) > 0 ? 1 : 0;
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
        mmap.put("SysArea", sysAreaService.selectTSysAreaById(id));
        List<TSysCity> sysCities = sysCityService.selectTSysCityList(new QueryWrapper<TSysCity>());
        mmap.put("sysCities", sysCities);
        return prefix + "/edit";
    }

    /**
     * 修改保存
     *
     * @param record
     * @return
     */
    @Log(title = "地区设置修改", action = "editSave")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:sysArea:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TSysArea record) {
        return toAjax(sysAreaService.updateTSysArea(record));
    }
}
