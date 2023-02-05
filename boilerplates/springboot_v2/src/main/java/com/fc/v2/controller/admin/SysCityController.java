package com.fc.v2.controller.admin;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysCity;
import com.fc.v2.model.auto.TSysProvince;
import com.fc.v2.service.ITSysCityService;
import com.fc.v2.service.ITSysProvinceService;
import com.fc.v2.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 城市Controller
 *
 * @author fuce
 * @ClassName: SysCityController
 * @date 2019-11-20 22:31
 */
@Api(value = "城市设置")
@Controller
@RequestMapping("/SysCityController")
public class SysCityController extends BaseController {

    private String prefix = "admin/sysProvince/sysCity";

    @Autowired
    private ITSysCityService sysCityService;

    @Autowired
    private ITSysProvinceService sysProvinceService;

    /**
     * 城市设置展示跳转
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("gen:sysCity:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 城市设置list
     *
     * @param tSysCity
     * @return
     */
    @Log(title = "城市设置集合查询", action = "/list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("gen:sysCity:list")
    @ResponseBody
    public ResultTable list(TSysCity tSysCity) {
        QueryWrapper<TSysCity> queryWrapper = new QueryWrapper<TSysCity>();
        queryWrapper.eq(StringUtils.isNotEmpty(tSysCity.getCityCode()), "city_code", tSysCity.getCityCode());
        queryWrapper.like(StringUtils.isNotEmpty(tSysCity.getCityName()), "city_name", tSysCity.getCityName());

        startPage();
        PageInfo<TSysCity> page = new PageInfo<TSysCity>(sysCityService.selectTSysCityList(queryWrapper));
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
        List<TSysProvince> sysProvinces = sysProvinceService.selectTSysProvinceList(new QueryWrapper<TSysProvince>());
        modelMap.put("sysProvinces", sysProvinces);
        return prefix + "/add";
    }

    /**
     * 新增保存
     *
     * @param sysCity
     * @return
     */
    @Log(title = "城市设置新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("gen:sysCity:add")
    @ResponseBody
    public AjaxResult add(TSysCity sysCity) {
        return toAjax(sysCityService.insertTSysCity(sysCity));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log(title = "城市设置删除", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("gen:sysCity:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysCityService.deleteTSysCityByIds(ids));
    }

    /**
     * 检查
     *
     * @param sysCity
     * @return
     */
    @ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysCity sysCity) {
        return sysCityService.checkNameUnique(sysCity) > 0 ? 1 : 0;
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
        List<TSysProvince> sysProvinces = sysProvinceService.selectTSysProvinceList(new QueryWrapper<TSysProvince>());
        mmap.put("sysProvinces", sysProvinces);
        mmap.put("SysCity", sysCityService.selectTSysCityById(id));
        return prefix + "/edit";
    }

    /**
     * 修改保存
     *
     * @param record
     * @return
     */
    @Log(title = "城市设置修改", action = "editSave")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:sysCity:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TSysCity record) {
        return toAjax(sysCityService.updateTSysCity(record));
    }
}
