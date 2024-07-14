package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResuTree;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysPermission;
import com.fc.v2.shiro.util.ShiroUtils;
import com.fc.v2.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 权限Controller
 *
 * @author fuce
 * @date: 2018年9月2日 下午8:08:21
 */
@Api(value = "权限")
@Controller
@RequestMapping("/PermissionController")
public class SysPermissionController extends BaseController {

    /**
     * 跳转页面参数
     */
    private final String prefix = "admin/sysPermission";

    /**
     * 权限列表展示
     *
     * @param model
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午3:43:51
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("system:permission:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 权限列表
     *
     * @param tSysPermission
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list")
    @RequiresPermissions("system:permission:list")
    @ResponseBody
    public ResultTable list(TSysPermission tSysPermission) {
        QueryWrapper<TSysPermission> queryWrapper = new QueryWrapper<TSysPermission>();
        queryWrapper.orderByAsc("order_num");
        queryWrapper.like(StringUtils.isNotEmpty(tSysPermission.getName()), "name", tSysPermission.getName());

        List<TSysPermission> list = sysPermissionService.selectTSysPermissionList(queryWrapper);
        return treeTable(list);
    }

    /**
     * 新增权限
     */
    @ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }


    /**
     * 权限添加
     *
     * @param tsysPermission
     * @return
     */
    @Log(title = "权限添加", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("system:permission:add")
    @ResponseBody
    public AjaxResult add(@RequestBody TSysPermission tsysPermission) {
        return toAjax(sysPermissionService.insertTSysPermission(tsysPermission));
    }

    /**
     * 删除权限
     *
     * @param ids
     * @return
     */
    @Log(title = "删除权限", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("system:permission:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        ImmutablePair<Integer, String> pair = sysPermissionService.deleteTSysPermissionByIds(ids);
        return pair.getLeft() < 0 ? error(pair.getRight()) : success();
    }

    /**
     * 检查权限
     *
     * @param tSysPermission
     * @return
     */
    @ApiOperation(value = "检查权限", notes = "检查权限")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysPermission tSysPermission) {
        return sysPermissionService.checkNameUnique(tSysPermission) > 0 ? 1 : 0;
    }

    /**
     * 检查权限URL
     *
     * @param tsysPermission
     * @return
     */
    @ApiOperation(value = "检查权限URL", notes = "检查权限URL")
    @PostMapping("/checkURLUnique")
    @ResponseBody
    public int checkURLUnique(@RequestBody TSysPermission tsysPermission) {
        return sysPermissionService.checkURLUnique(tsysPermission) > 0 ? 1 : 0;
    }

    /**
     * 检查权限perms字段
     *
     * @param tsysPermission
     * @return
     */
    @ApiOperation(value = "检查权限perms字段", notes = "检查权限perms字段")
    @PostMapping("/checkPermsUnique")
    @ResponseBody
    public int checkPermsUnique(TSysPermission tsysPermission) {
        return sysPermissionService.checkPermsUnique(tsysPermission) > 0 ? 1 : 0;
    }

    /**
     * 修改权限
     *
     * @param id
     * @param mmap
     * @return
     */
    @ApiOperation(value = "修改跳转", notes = "修改跳转")
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long id, ModelMap mmap) {
        //获取自己的权限信息
        TSysPermission mytsysPermission = sysPermissionService.selectTSysPermissionById(id);
        //获取父权限信息
        TSysPermission pattsysPermission = sysPermissionService.selectTSysPermissionById(mytsysPermission.getPid());
        mmap.put("TsysPermission", mytsysPermission);
        mmap.put("pattsysPermission", pattsysPermission);
        return prefix + "/edit";
    }

    /**
     * 修改保存权限
     *
     * @param tSysPermission
     * @return
     */
    @Log(title = "修改保存权限", action = "editSave")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("system:permission:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody TSysPermission tSysPermission) {
        return toAjax(sysPermissionService.updateTSysPermission(tSysPermission));
    }


    /**
     * 根据角色id获取bootstarp 所有打勾权限
     *
     * @param roleId 角色id集合
     * @return
     */
    @ApiOperation(value = "根据角色id获取所有打勾权限", notes = "根据角色id获取 所有打勾权限")
    @GetMapping("/getCheckPrem")
    @ResponseBody
    public ResuTree getCheckPrem(Long roleId) {
        return dataTree(sysPermissionService.getRolePower(roleId));
    }


    /**
     * 跳转到菜单树页面
     *
     * @return
     */
    @ApiOperation(value = "跳转到菜单树页面", notes = "跳转到菜单树页面")
    @GetMapping("/tree/{roleId}")
    public String Tree(@PathVariable("roleId") String roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return prefix + "/tree";
    }


    /**
     * 修改保存角色
     */
    @Log(title = "修改保存角色", action = "saveRolePower")
    @ApiOperation(value = "授权保存", notes = "授权保存")
    @RequiresPermissions("system:role:edit")
    @PutMapping("/saveRolePower")
    @ResponseBody
    public AjaxResult saveRolePower(Long roleId, String powerIds) {
        int res = sysRoleService.updateRoleAndPrem(roleId, powerIds);
        if (res > 0) {
            //大于0刷新权限
            ShiroUtils.clearCachedAuthorizationInfo();
        }
        return toAjax(res);
    }


    @GetMapping("/selectParent")
    @ResponseBody
    public ResuTree selectParent() {
        List<TSysPermission> list = sysPermissionService.getall(null);
        TSysPermission basePower = new TSysPermission();
        basePower.setName("顶级权限");
        basePower.setId(0L);
        basePower.setPid(-1L);
        list.add(basePower);
        return dataTree(list);
    }

    @PutMapping("/updateVisible")
    @ResponseBody
    public AjaxResult updateVisible(@RequestBody TSysPermission tSysPermission) {
        int i = sysPermissionService.updateVisible(tSysPermission);
        return toAjax(i);
    }

}
