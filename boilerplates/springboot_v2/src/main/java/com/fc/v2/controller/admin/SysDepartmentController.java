package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysDepartment;
import com.fc.v2.common.domain.ResuTree;
import com.fc.v2.service.ITSysDepartmentService;
import com.fc.v2.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门 controller
 *
 * @author fuce
 * @date: 2018年9月30日 下午9:28:31
 */
@Api(value = "部门表")
@Controller
@RequestMapping("/SysDepartmentController")
public class SysDepartmentController extends BaseController {

    private final String prefix = "admin/sysDepartment";

    @Autowired
    private ITSysDepartmentService sysDepartmentService;

    /**
     * 分页跳转
     *
     * @param model
     * @return
     * @author fuce
     * @Date 2020年4月18日 下午11:48:55
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("gen:sysDepartment:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 部门查询, 不适用分页
     *
     * @param tSysDepartment
     * @return
     */
    @Log(title = "部门表集合查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("gen:sysDepartment:list")
    @ResponseBody
    public ResultTable list(TSysDepartment tSysDepartment) {
        QueryWrapper<TSysDepartment> queryWrapper = new QueryWrapper<TSysDepartment>();
        queryWrapper.orderByAsc("order_num");
        queryWrapper.like(StringUtils.isNotEmpty(tSysDepartment.getDeptName()), "dept_name", tSysDepartment.getDeptName());

        List<TSysDepartment> list = sysDepartmentService.selectTSysDepartmentList(queryWrapper);
        return treeTable(list);
    }

    /**
     * 新增
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
     * 新增保存
     *
     * @param sysDepartment
     * @return
     * @author fuce
     * @Date 2020年4月18日 下午11:47:51
     */
    @Log(title = "部门表新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("gen:sysDepartment:add")
    @ResponseBody
    public AjaxResult add(@RequestBody TSysDepartment sysDepartment) {
        return toAjax(sysDepartmentService.insertTSysDepartment(sysDepartment));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log(title = "部门表删除", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("gen:sysDepartment:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysDepartmentService.deleteTSysDepartmentByIds(ids));
    }

    /**
     * 检查用户
     *
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysDepartment sysDepartment) {
        return sysDepartmentService.checkNameUnique(sysDepartment) > 0 ? 1 : 0;
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
        //获取自己的权限信息
        TSysDepartment mytsysPermission = sysDepartmentService.selectTSysDepartmentById(id);
        mmap.put("SysDepartment", mytsysPermission);
        return prefix + "/edit";
    }

    /**
     * 修改保存
     *
     * @param record
     * @return
     */
    @Log(title = "部门表修改", action = "editSave")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:sysDepartment:edit")
    @PutMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody TSysDepartment record) {
        return toAjax(sysDepartmentService.updateTSysDepartment(record));
    }


    /**
     * @return
     */
    @GetMapping("/selectParent")
    @ResponseBody
    public ResuTree selectParent() {
        List<TSysDepartment> list = sysDepartmentService.selectTSysDepartmentList(new QueryWrapper<TSysDepartment>());
        return dataTree(list);
    }


    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    @PostMapping("/get/{id}")
    @ApiOperation(value = "根据id查询唯一", notes = "根据id查询唯一")
    public TSysDepartment edit(@PathVariable("id") Long id) {
        return sysDepartmentService.selectTSysDepartmentById(id);
    }

    /**
     * 跳转树形列表
     *
     * @return
     */
    @ApiOperation(value = "跳转树形列表", notes = "跳转树形列表")
    @GetMapping("/deptTree")
    public String deptTree() {
        return prefix + "/dept-tree.html";
    }

    /**
     * 获取部门树状数据结构
     *
     * @return
     */
    @GetMapping("tree")
    @ApiOperation(value = "获取部门树状数据结构", notes = "获取部门树状数据结构")
    @ResponseBody
    public ResuTree tree() {
        List<TSysDepartment> data = sysDepartmentService.selectTSysDepartmentList(new QueryWrapper<TSysDepartment>());
        return dataTree(data);
    }

    /**
     * 修改状态
     *
     * @param record
     * @return
     */
    @PutMapping("/updateVisible")
    @ResponseBody
    public AjaxResult updateVisible(@RequestBody TSysDepartment record) {
        return toAjax(sysDepartmentService.updateVisible(record));
    }
}
