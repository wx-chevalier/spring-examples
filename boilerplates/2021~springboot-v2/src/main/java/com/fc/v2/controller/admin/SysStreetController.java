package com.fc.v2.controller.admin;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysArea;
import com.fc.v2.model.auto.TSysStreet;
import com.fc.v2.service.ITSysAreaService;
import com.fc.v2.service.ITSysStreetService;
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
 * 街道Controller
 *
 * @author fuce
 * @ClassName: SysStreetController
 * @date 2019-11-20 22:32
 */
@Api(value = "街道设置")
@Controller
@RequestMapping("/SysStreetController")
public class SysStreetController extends BaseController {

    private String prefix = "admin/sysProvince/sysStreet";

    @Autowired
    private ITSysStreetService sysStreetService;

    @Autowired
    private ITSysAreaService sysAreaService;

    /**
     * 展示跳转
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("gen:sysStreet:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 街道查询
     *
     * @param tSysStreet
     * @return
     */
    @Log(title = "街道设置集合查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("gen:sysStreet:list")
    @ResponseBody
    public ResultTable list(TSysStreet tSysStreet) {
        QueryWrapper<TSysStreet> queryWrapper = new QueryWrapper<TSysStreet>();
        queryWrapper.eq(StringUtils.isNotEmpty(tSysStreet.getStreetCode()), "street_code", tSysStreet.getStreetCode());
        queryWrapper.like(StringUtils.isNotEmpty(tSysStreet.getStreetName()), "street_name", tSysStreet.getStreetName());

        startPage();
        PageInfo<TSysStreet> page = new PageInfo<TSysStreet>(sysStreetService.selectTSysStreetList(queryWrapper));
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
        List<TSysArea> sysAreas = sysAreaService.selectTSysAreaList(new QueryWrapper<TSysArea>());
        modelMap.put("sysAreas", sysAreas);
        return prefix + "/add";
    }

    /**
     * 新增保存
     *
     * @param sysStreet
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:13:37
     */
    @Log(title = "街道设置新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("gen:sysStreet:add")
    @ResponseBody
    public AjaxResult add(TSysStreet sysStreet) {
        return toAjax(sysStreetService.insertTSysStreet(sysStreet));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log(title = "街道设置删除", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("gen:sysStreet:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysStreetService.deleteTSysStreetByIds(ids));
    }

    /**
     * 检查
     *
     * @param sysStreet
     * @return
     */
    @ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysStreet sysStreet) {
        return sysStreetService.checkNameUnique(sysStreet) > 0 ? 1 : 0;
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
        List<TSysArea> sysAreas = sysAreaService.selectTSysAreaList(new QueryWrapper<TSysArea>());
        mmap.put("SysStreet", sysStreetService.selectTSysStreetById(id));
        mmap.put("sysAreas", sysAreas);
        return prefix + "/edit";
    }

    /**
     * 修改保存
     *
     * @param record
     * @return
     */
    @Log(title = "街道设置修改", action = "editSave")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:sysStreet:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TSysStreet record) {
        return toAjax(sysStreetService.updateTSysStreet(record));
    }
}
