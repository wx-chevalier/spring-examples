package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysProvince;
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
 * 省份Controller
 *
 * @author fuce
 * @ClassName: SysProvinceController
 * @date 2019-11-20 22:32
 */
@Api(value = "省份表")
@Controller
@RequestMapping("/SysProvinceController")
public class SysProvinceController extends BaseController {

    private String prefix = "admin/sysProvince/sysProvince";

    @Autowired
    private ITSysProvinceService sysProvinceService;

    /**
     * 展示页面跳转
     *
     * @param model
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:10:48
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("gen:sysProvince:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 省份列表
     *
     * @param tSysProvince
     * @return
     */
    @Log(title = "省份表集合查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("gen:sysProvince:list")
    @ResponseBody
    public ResultTable list(TSysProvince tSysProvince) {
        QueryWrapper<TSysProvince> queryWrapper = new QueryWrapper<TSysProvince>();
        queryWrapper.eq(StringUtils.isNotEmpty(tSysProvince.getProvinceCode()), "province_code", tSysProvince.getProvinceCode());
        queryWrapper.like(StringUtils.isNotEmpty(tSysProvince.getProvinceName()), "province_name", tSysProvince.getProvinceName());

        startPage();
        PageInfo<TSysProvince> page = new PageInfo<TSysProvince>(sysProvinceService.selectTSysProvinceList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    /**
     * 新增跳转
     *
     * @param modelMap
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:10:20
     */
    @ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        return prefix + "/add";
    }

    /**
     * 新增保存
     *
     * @param sysProvince
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:10:26
     */
    @Log(title = "省份表新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("gen:sysProvince:add")
    @ResponseBody
    public AjaxResult add(TSysProvince sysProvince) {
        return toAjax(sysProvinceService.insertTSysProvince(sysProvince));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log(title = "省份表删除", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("gen:sysProvince:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProvinceService.deleteTSysProvinceByIds(ids));
    }

    /**
     * 检查
     *
     * @param sysProvince
     * @return
     */
    @ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysProvince sysProvince) {
        return sysProvinceService.checkNameUnique(sysProvince) > 0 ? 1 : 0;
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
        mmap.put("SysProvince", sysProvinceService.selectTSysProvinceById(id));
        return prefix + "/edit";
    }

    /**
     * 修改保存
     *
     * @param record
     * @return
     */
    @Log(title = "省份表修改", action = "editSave")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:sysProvince:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TSysProvince record) {
        return toAjax(sysProvinceService.updateTSysProvince(record));
    }
}
