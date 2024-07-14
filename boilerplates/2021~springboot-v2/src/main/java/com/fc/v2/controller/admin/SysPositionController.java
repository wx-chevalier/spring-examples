package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysPosition;
import com.fc.v2.service.ITSysPositionService;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 岗位 controller
 *
 * @author fuce
 * @date: 2018年9月30日 下午9:28:31
 */
@Controller
@RequestMapping("/SysPositionController")
@Api(value = "岗位表")
public class SysPositionController extends BaseController {

    private final String prefix = "admin/sysPosition";

    @Autowired
    private ITSysPositionService sysPositionService;

    /**
     * list展示
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("gen:sysPosition:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 岗位查询
     *
     * @param tSysPosition
     * @return
     */
    @Log(title = "岗位表集合查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list")
    @RequiresPermissions("gen:sysPosition:list")
    @ResponseBody
    public ResultTable list(TSysPosition tSysPosition) {
        QueryWrapper<TSysPosition> queryWrapper = new QueryWrapper<TSysPosition>();
        queryWrapper.like(StringUtils.isNotEmpty(tSysPosition.getPostName()), "post_name", tSysPosition.getPostName());
        queryWrapper.eq(StringUtils.isNotNull(tSysPosition.getStatus()), "status", tSysPosition.getStatus());

        startPage();
        PageInfo<TSysPosition> page =  new PageInfo<TSysPosition>(sysPositionService.selectTSysPositionList(queryWrapper));
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
        return prefix + "/add";
    }

    /**
     * 新增
     * 
     * @param sysPosition
     * @return
     */
    @Log(title = "岗位表新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("add")
    @RequiresPermissions("gen:sysPosition:add")
    @ResponseBody
    public AjaxResult add(TSysPosition sysPosition) {
        if (sysPositionService.checkNameUnique(sysPosition) > 0){
            return error("岗位名称重复");
        }
        return toAjax(sysPositionService.insertTSysPosition(sysPosition));
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    @Log(title = "岗位表删除", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("gen:sysPosition:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysPositionService.deleteTSysPositionByIds(ids));
    }

    /**
     * 检查用户
     *
     * @param sysPosition
     * @return
     */
    @ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysPosition sysPosition) {
        return sysPositionService.checkNameUnique(sysPosition) > 0 ? 1 : 0;
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
        mmap.put("SysPosition", sysPositionService.selectTSysPositionById(id));
        return prefix + "/edit";
    }

    /**
     * 修改保存
     */
    @Log(title = "岗位表修改", action = "editSave")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:sysPosition:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TSysPosition record) {
        return toAjax(sysPositionService.updateTSysPosition(record));
    }


    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    @PostMapping("/get/{id}")
    @ApiOperation(value = "根据id查询唯一", notes = "根据id查询唯一")
    public TSysPosition edit(@PathVariable("id") Long id) {
        return sysPositionService.selectTSysPositionById(id);
    }


    /**
     * 修改状态
     *
     * @param record
     * @return
     */
    @PutMapping("/updateVisible")
    @ResponseBody
    public AjaxResult updateVisible(@RequestBody TSysPosition record) {
        return toAjax(sysPositionService.updateVisible(record));
    }
}
