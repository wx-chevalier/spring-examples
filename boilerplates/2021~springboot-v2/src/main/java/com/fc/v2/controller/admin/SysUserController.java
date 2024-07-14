package com.fc.v2.controller.admin;

import java.util.List;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.conf.V2Config;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.model.auto.TSysDepartment;
import com.fc.v2.model.auto.TSysPosition;
import com.fc.v2.model.auto.TSysRole;
import com.fc.v2.model.auto.TSysUser;
import com.fc.v2.service.ITSysDepartmentService;
import com.fc.v2.service.ITSysPositionService;
import com.fc.v2.shiro.util.ShiroUtils;
import com.fc.v2.util.MD5Util;
import com.fc.v2.util.file.FileUploadUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.custom.RoleVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户Controller
 *
 * @author fuce
 * @ClassName: UserController
 * @date 2019-11-20 22:35
 */
@Api(value = "用户数据")
@Controller
@RequestMapping("/UserController")
public class SysUserController extends BaseController {

    private final String prefix = "admin/sysUser";

    /**
     * 部门
     */
    @Autowired
    private ITSysDepartmentService departmentService;

    /**
     * 岗位
     */
    @Autowired
    private ITSysPositionService positionService;

    /**
     * v2配置
     */
    @Autowired
    private V2Config config;

    /**
     * 展示跳转页面
     *
     * @param model
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:14:34
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("system:user:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 用户列表
     *
     * @param tSysUser
     * @return
     */
    @Log(title = "分页查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("system:user:list")
    @ResponseBody
    public ResultTable list(TSysUser tSysUser) {
        startPage();
        PageInfo<TSysUser> page = new PageInfo<TSysUser>(sysUserService.selectTSysUserList(tSysUser));
        return pageTable(page.getList(), page.getTotal());
    }

    /**
     * 按部门查询用户列表
     *
     * @param deptIds
     * @return
     */
    @Log(title = "分页查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/dept/list")
    @RequiresPermissions("system:user:list")
    @ResponseBody
    public ResultTable list(String deptIds) {
        startPage();
        PageInfo<TSysUser> page = new PageInfo<TSysUser>(sysUserService.selectTSysUserByDepIdList(deptIds));
        return pageTable(page.getList(), page.getTotal());
    }

    /**
     * 新增跳转
     *
     * @param modelMap
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:14:51
     */
    @ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        //添加角色列表
        List<TSysRole> tsysRoleList = sysRoleService.selectTSysRoleList(new QueryWrapper<TSysRole>());
        //部门列表
        List<TSysDepartment> departments = departmentService.selectTSysDepartmentList(new QueryWrapper<TSysDepartment>());
        //岗位列表
        List<TSysPosition> sysPositions = positionService.selectTSysPositionList(new QueryWrapper<TSysPosition>());
        //角色
        modelMap.put("tsysRoleList", tsysRoleList);
        //部门
        modelMap.put("departmentsList", departments);
        //岗位
        modelMap.put("sysPositionsList", sysPositions);
        return prefix + "/add";
    }

    /**
     * 新增保存
     *
     * @param user
     * @param roleIds
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:14:57
     */
    @Log(title = "用户新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("system:user:add")
    @ResponseBody
    public AjaxResult add(TSysUser user, @RequestParam(value = "roleIds", required = false) String roleIds) {
        if (sysUserService.checkLoginNameUnique(user) > 0){
            return error("用户账号重复");
        }
        return toAjax(sysUserService.insertUserRoles(user, roleIds));
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    @Log(title = "删除用户", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("system:user:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysUserService.deleteTSysUserByIds(ids));
    }

    /**
     * 修改用户跳转
     *
     * @param id
     * @param mmap
     * @return
     */
    @ApiOperation(value = "修改跳转", notes = "修改跳转")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        //查询所有角色
        List<RoleVo> roleVos = sysUserService.getUserIsRole(id);
        //岗位列表
        List<TSysPosition> sysPositions = positionService.selectTSysPositionList(new QueryWrapper<TSysPosition>());
        mmap.put("roleVos", roleVos);
        mmap.put("TsysUser", sysUserService.selectTSysUserById(id));
        //岗位
        mmap.put("sysPositionsList", sysPositions);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     * 
     * @param tsysUser
     * @param roleIds
     * @return
     */
    @Log(title = "修改保存用户", action = "editSave")
    @ApiOperation(value = "修改保存用户", notes = "修改保存用户")
    @RequiresPermissions("system:user:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TSysUser tsysUser, @RequestParam(value = "roleIds", required = false) String roleIds) {
        return toAjax(sysUserService.updateUserRoles(tsysUser, roleIds));
    }


    /**
     * 修改用户密码跳转
     *
     * @param id
     * @param mmap
     * @return
     */
    @Log(title = "修改用户密码", action = "editPwd")
    @ApiOperation(value = "修改用户密码跳转", notes = "修改用户密码跳转")
    @GetMapping("/editPwd/{id}")
    public String editPwd(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("TsysUser", sysUserService.selectTSysUserById(id));
        return prefix + "/editPwd";
    }

    /**
     * 修改保存用户
     * 
     * @param tsysUser
     * @return
     */
    @Log(title = "修改用户密码", action = "editPwdSave")
    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @RequiresPermissions("system:user:editPwd")
    @PostMapping("/editPwd")
    @ResponseBody
    public AjaxResult editPwdSave(TSysUser tsysUser) {
        return toAjax(sysUserService.updateUserPassword(tsysUser));
    }

    /**
     * 个人详细信息
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "个人详细信息", notes = "个人详细信息")
    @GetMapping("/user/person")
    public String userInfo(ModelMap map){
        TSysUser tSysUser = ShiroUtils.getUser();
        map.put("user", tSysUser);
        map.put("fuce", config);
        return prefix + "/person.html";
    }

    /**
     * 跳转上传页面
     *
     * @return
     */
    @ApiOperation(value = "跳转上传页面", notes = "跳转上传页面")
    @GetMapping("/user/uploadProfile")
    public String uploadProfile(){
        return prefix + "/uploadProfile.html";
    }

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "上传头像", notes = "上传头像")
    @PostMapping("/user/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(MultipartFile file){

        TSysUser tSysUser = ShiroUtils.getUser();

        try {
            if (!file.isEmpty()){
                String avatar =  FileUploadUtils.upload(config.getProfile(), config.getAvatarPath(), file);
                tSysUser.setAvatar(avatar);
                if (sysUserService.updateTSysUser(tSysUser) > 0){
                    ShiroUtils.setUser(sysUserService.selectTSysUserById(tSysUser.getId()));
                    return success(HttpStatus.HTTP_OK,"操作成功", avatar);
                }
            }
            return error();
        }catch (Exception e){
            return error(e.getMessage());
        }
    }

    /**
     * 修改保存用户
     *
     * @param tsysUser
     * @return
     */
    @Log(title = "修改保存用户", action = "update")
    @ApiOperation(value = "修改保存用户", notes = "修改保存用户")
    @PostMapping("/user/update")
    @ResponseBody
    public AjaxResult update(TSysUser tsysUser) {
        if (sysUserService.updateTSysUser(tsysUser) > 0){
            ShiroUtils.setUser(sysUserService.selectTSysUserById(tsysUser.getId()));
            return success();
        }
        return error();
    }

    /**
     * 重置密码
     *
     * @param tsysUser
     * @return
     */
    @Log(title = "重置密码", action = "update")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @PostMapping("/user/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(TSysUser tsysUser, String oldPassword, String newPassword, String confirmPassword) {

        if (!MD5Util.encode(newPassword).equals(MD5Util.encode(confirmPassword))){
            return error("两次密码输入不一致");
        }

        tsysUser = sysUserService.selectTSysUserById(tsysUser.getId());

        if (!MD5Util.encode(oldPassword).equals(tsysUser.getPassword())){
            return error("修改密码失败，旧密码错误");
        }

        if (MD5Util.encode(newPassword).equals(tsysUser.getPassword())){
            return error("新密码不能与旧密码相同");
        }

        tsysUser.setPassword(MD5Util.encode(newPassword));
        if (sysUserService.updateTSysUser(tsysUser) > 0){
            ShiroUtils.setUser(sysUserService.selectTSysUserById(tsysUser.getId()));
            return success();
        }

        return error("修改密码异常，请联系管理员");
    }
}
