package com.cloud.user.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.exceptionHandler.GFCException;
import com.cloud.utils.AppUserUtil;
import com.cloud.utils.ComUtil;
import com.cloud.utils.ResultHelper;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.constants.RoleType;
import com.cloud.user.service.AppUserService;
import com.cloud.user.service.ISysSwaggerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private AppUserService appUserService;

    /**
     * 当前登录用户 LoginAppUser
     */
    @GetMapping("/users/current")
    @ApiOperation(value = "获取当前登录用户")
    public ResultHelper getLoginAppUser() {
//        return ResultHelper.succeed(AppUserUtil.getLoginAppUser());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        return ResultHelper.succeed(authentication.getPrincipal());
    }

    @ApiIgnore
    @GetMapping(value = "/users-anon/internal", params = "username")
    public ResultHelper findByUsername(String username) {
        return ResultHelper.succeed(appUserService.findByUsername(username));
    }

    /**
     * 用户查询
     */
    @ApiOperation(value = "用户分页查询")
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users")
    public ResultHelper findUsers(
            @RequestParam(name = "current", defaultValue = "1", required = false) Integer current,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "username", defaultValue = "", required = false) String username,
            @RequestParam(name = "nickname", defaultValue = "", required = false) String nickname,
            @RequestParam(name = "enabled", defaultValue = "", required = false) Boolean enabled,
            @RequestParam(name = "type", defaultValue = "", required = false) String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("username",username);
        params.put("nickname",nickname);
        if(!ComUtil.isEmpty(enabled)){
            params.put("enabled",enabled == true?"1":"0");
        }
        params.put("type",type);
        return ResultHelper.succeed(appUserService.findUsersByPage(new Page<AppUser>(current,size),params));
    }

    @ApiOperation(value = "根据id查询用户")
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users/{id}")
    public ResultHelper findUserById(@PathVariable String id) {
        AppUser appUser = appUserService.selectById(id);
        appUser.setRole(appUserService.findRoleByUserId(appUser.getId()).getCode());
        return ResultHelper.succeed(appUser);
    }

    @ApiOperation(value = "根据id删除用户")
    @DeleteMapping("/users/{id}")
    public ResultHelper deleteUserById(@PathVariable String id) throws Exception{
        AppUser appUser = appUserService.selectById(id);
        if(ComUtil.isEmpty(appUser)){
            throw new GFCException("用户不存在");
        }
        appUserService.deleteUser(appUser);
        return ResultHelper.succeed(appUser);
    }


    /**
     * 添加用户,根据用户名注册
     * @param appUser
     */
    @ApiOperation(value = "添加用户,修改用户")
    @PostMapping("/users/register")
    public ResultHelper register(@RequestBody AppUser appUser)throws Exception {
        // 用户名等信息的判断逻辑挪到service了
        appUserService.addAppUser(appUser);

        return ResultHelper.succeed(appUser);
    }

    /**
     * 添加用户,根据用户名注册
     * @param appUser
     */
    @ApiOperation(value = "用户注册(匿名)")
    @PostMapping("/users-anon/register")
    public ResultHelper usersAnonRegiste(@RequestBody AppUser appUser)throws Exception {
        appUser.setRole(RoleType.USER.name());
        appUser.setType("5");
        appUserService.addAppUser(appUser);
        return ResultHelper.succeed(appUser);
    }

    /**
     * 修改自己的个人信息
     *
     * @param appUser
     */
    @ApiOperation(value = "修改自己的个人信息")
//    @LogAnnotation(module = LogModule.UPDATE_ME)
    @PutMapping("/users/me")
    public ResultHelper updateMe(@RequestBody AppUser appUser) {
        AppUser user = AppUserUtil.getLoginAppUser();
        appUser.setId(user.getId());
        appUserService.updateById(appUser);
        return ResultHelper.succeed(appUser);
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @ApiOperation(value = "修改密码")
//    @LogAnnotation(module = LogModule.UPDATE_PASSWORD)
    @PutMapping(value = "/users/password", params = {"oldPassword", "newPassword"})
    public void updatePassword(String oldPassword, String newPassword)throws Exception {
        if (StringUtils.isBlank(oldPassword)) {
            throw new GFCException("旧密码不能为空");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new GFCException("新密码不能为空");
        }

        AppUser user = AppUserUtil.getLoginAppUser();
        appUserService.updatePassword(user.getId(), oldPassword, newPassword);
    }

    /**
     * 管理后台，给用户重置密码
     *
     * @param id          用户id
     * @param newPassword 新密码
     */
//    @LogAnnotation(module = LogModule.RESET_PASSWORD)
    @PreAuthorize("hasAuthority('back:user:password')")
    @ApiOperation(value = "管理后台，给用户重置密码")
    @PutMapping(value = "/users/{id}/password", params = {"newPassword"})
    public ResultHelper resetPassword(@PathVariable String id, String newPassword) throws Exception{
        return ResultHelper.succeed(appUserService.updatePassword(id, null, newPassword));
    }

    /**
     * 管理后台修改用户
     *
     * @param appUser
     */
    @ApiOperation(value = "管理后台修改用户信息")
//    @LogAnnotation(module = LogModule.UPDATE_USER)
    @PreAuthorize("hasAuthority('back:user:update')")
    @PutMapping("/users")
    public void updateAppUser(@RequestBody AppUser appUser)throws Exception {
        appUserService.updateAppUser(appUser);
    }

    /**
     * 管理后台给用户分配角色
     *
     * @param id      用户id
     * @param roleIds 角色ids
     */
    @ApiOperation(value = "管理后台给用户分配角色")
//    @LogAnnotation(module = LogModule.SET_ROLE)
    @PreAuthorize("hasAuthority('back:user:role:set')")
    @PostMapping("/users/{id}/roles")
    public void setRoleToUser(@PathVariable String id, @RequestBody Set<Long> roleIds) throws Exception{
        appUserService.setRoleToUser(id, roleIds);
    }

    /**
     * 获取用户的角色
     *
     * @param id 用户id
     */
    @ApiOperation(value = "获取用户的角色")
    @PreAuthorize("hasAnyAuthority('back:user:role:set','user:role:byuid')")
    @GetMapping("/users/{id}/roles")
    public ResultHelper findRolesByUserId(@PathVariable String id) {
        return ResultHelper.succeed(appUserService.findRolesByUserId(id));
    }

    /**
     * 根据昵称进行模糊查询用户
     * @param nickName  用户昵称
     * @return
     */
   @ApiOperation(value = "根据昵称进行模糊查询用户")
    @GetMapping("/users/byNickName")
    public List<AppUser> findByNickName(@RequestParam String nickName){
       return appUserService.findByNickName(nickName);
    }






    @Autowired
    private ISysSwaggerService sysSwaggerService;

    @ApiOperation(value = "swagger页面接口")
    @GetMapping("/swaggerList")
    public ResultHelper findRoles() {
        return ResultHelper.succeed(sysSwaggerService.selectList(new EntityWrapper<>()));
    }
}
