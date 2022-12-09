package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.exceptionHandler.GFCException;
import com.cloud.utils.ComUtil;
import com.cloud.utils.GenerationSequenceUtil;
import com.cloud.utils.PhoneUtil;
import com.cloud.model.user.*;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.model.user.constants.RoleType;
import com.cloud.user.dao.AppUserDao;
import com.cloud.user.dao.UserCredentialsDao;
import com.cloud.user.dao.UserRoleDao;
import com.cloud.user.service.AppUserService;
import com.cloud.user.service.SysPermissionService;
import com.cloud.user.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppUserServiceImpl  extends ServiceImpl<AppUserDao, AppUser> implements AppUserService {

    @Autowired
    private AppUserDao appUserDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserCredentialsDao userCredentialsDao;

    @Autowired
    private SysRoleService sysRoleService;

    @Transactional
    @Override
    public AppUser addAppUser(AppUser appUser) throws Exception{
        String username = appUser.getUsername();
        if (StringUtils.isBlank(username)) {
            throw new GFCException("用户名不能为空");
        }
        if (PhoneUtil.checkPhone(username)) {// 防止用手机号直接当用户名，手机号要发短信验证
            throw new GFCException("用户名要包含英文字符");
        }
        if (username.contains("@")) {// 防止用邮箱直接当用户名，邮箱也要发送验证（暂未开发）
            throw new GFCException("用户名不能包含@");
        }
        if (username.contains("|")) {
            throw new GFCException("用户名不能包含|字符");
        }
        if (StringUtils.isBlank(appUser.getNickname())) {
            appUser.setNickname(username);
        }
        if (StringUtils.isBlank(appUser.getType())) {
            appUser.setType(appUser.getType());
        }
        if(appUser.getRole().equals(RoleType.SUPER_ADMIN.name()) ){
            throw new GFCException("不能添加管理员");
        }
        SysRole role = sysRoleService.selectOne(new EntityWrapper<SysRole>().eq("code", appUser.getRole()));
        if(ComUtil.isEmpty(role)){
            throw new GFCException("角色不存在");
        }
        appUser.setEnabled(appUser.getEnabled());
        appUser.setCreateTime(System.currentTimeMillis());
        appUser.setUpdateTime(appUser.getCreateTime());
        appUser.setOrganizationId(1);//随便默认值
        if(ComUtil.isEmpty(appUser.getId())){
            if (StringUtils.isBlank(appUser.getPassword())) {
                throw new GFCException("密码不能为空");
            }
            if (!appUser.getPassword().equals(appUser.getRePassword())) {
                throw  new GFCException("两次密码不一致");
            }
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword())); // 加密密码
            UserCredential userCredential = userCredentialsDao.findByUsername(appUser.getUsername());
            if (userCredential != null) {
                throw new GFCException("用户名已存在");
            }
            appUser.setId(GenerationSequenceUtil.generateUUID());
            this.insert(appUser);
            userRoleDao.saveUserRoles(appUser.getId(),role.getId());
            userCredentialsDao
                    .save(new UserCredential(appUser.getUsername(), CredentialType.USERNAME.name(), appUser.getId()));
            log.info("添加用户：{}", appUser);
        }else {
            AppUser oldUser = this.selectById(appUser.getId());
            Set<SysRole> oldRoles = userRoleDao.findRolesByUserId(oldUser.getId());
            for (SysRole oldRole:oldRoles) {
                userRoleDao.deleteUserRole(oldUser.getId(),oldRole.getId());
            }
            userRoleDao.saveUserRoles(appUser.getId(),role.getId());
            userCredentialsDao.deleteByUserId(oldUser.getId());
            userCredentialsDao.save(new UserCredential(appUser.getUsername(), CredentialType.USERNAME.name(), appUser.getId()));
            this.updateById(appUser);
            log.info("修改用户：{}", appUser);
        }

        return appUser;


    }

    @Transactional
    @Override
    public AppUser updateAppUser(AppUser appUser) {
        appUser.setUpdateTime(System.currentTimeMillis());
        appUserDao.updateById(appUser);
        log.info("修改用户：{}", appUser);
        return  appUser;
    }

    @Transactional
    @Override
    public LoginAppUser findByUsername(String username) {
        AppUser appUser = appUserDao.findUserByUsername(username);
        return setUserRoles(appUser);
    }

    /**
     * 给用户设置角色<br>
     * 这里采用先删除老角色，再插入新角色
     */
    @Transactional
    @Override
    public void setRoleToUser(String id, Set<Long> roleIds) throws Exception{
        AppUser appUser = appUserDao.selectById(id);
        if (appUser == null) {
            throw new GFCException("用户不存在");
        }

        userRoleDao.deleteUserRole(id, null);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                userRoleDao.saveUserRoles(id, roleId);
            });
        }

        log.info("修改用户：{}的角色，{}", appUser.getUsername(), roleIds);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     */
    @Transactional
    @Override
    public AppUser updatePassword(String id, String oldPassword, String newPassword) throws Exception{
        AppUser appUser = appUserDao.selectById(id);
        if(ComUtil.isEmpty(appUser)){
            throw  new GFCException("用户不存在");
        }
        if(this.isAdmin(appUser.getUsername())){
            throw  new GFCException("不能重置管理员密码");
        }
        if (StringUtils.isNoneBlank(oldPassword)) {
            if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) { // 旧密码校验
                throw new GFCException("旧密码错误");
            }
        }
        appUser.setPassword(passwordEncoder.encode(newPassword));
        this.updateById(appUser);
        log.info("修改密码：{}", appUser);
        return  appUser;
    }

    @Override
    public Set<SysRole> findRolesByUserId(String userId) {
        return userRoleDao.findRolesByUserId(userId);
    }

    private boolean isAdmin(String username){
        if (!ComUtil.isEmpty(username)){
            if("admin".equals(username) || "superadmin".equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * 绑定手机号
     */
    @Transactional
    @Override
    public void bindingPhone(String userId, String phone) throws Exception{
        UserCredential userCredential = userCredentialsDao.findByUsername(phone);
        if (userCredential != null) {
            throw new GFCException("手机号已被绑定");
        }

        AppUser appUser = appUserDao.selectById(userId);
        appUser.setPhone(phone);

        updateAppUser(appUser);
        log.info("绑定手机号成功,username:{}，phone:{}", appUser.getUsername(), phone);

        // 绑定成功后，将手机号存到用户凭证表，后续可通过手机号+密码或者手机号+短信验证码登陆
        userCredentialsDao.save(new UserCredential(phone, CredentialType.PHONE.name(), userId));
    }

    @Override
    public Page<AppUser> findUsersByPage(Page<AppUser> appUserPage, Map<String, Object> params) {
        List<AppUser> usersByPage = appUserDao.findUsersByPage(appUserPage, params);
        for (AppUser appUser:usersByPage) {
              SysRole roleByUserId = userRoleDao.findRoleByUserId(appUser.getId());
             appUser.setRole(roleByUserId.getCode());
        }
        return appUserPage.setRecords(usersByPage);
    }

    @Override
    public SysRole findRoleByUserId(String id){
        return userRoleDao.findRoleByUserId(id);
    }

    @Override
    @Transactional
    public AppUser deleteUser(AppUser appUser) throws Exception {
        if(this.isAdmin(appUser.getUsername())){
            throw new GFCException("不能删除管理员");
        }
        this.deleteById(appUser.getId());
        userCredentialsDao.deleteByUserId(appUser.getId());
        SysRole sysRole = userRoleDao.findRoleByUserId(appUser.getId());
        userRoleDao.deleteUserRole(appUser.getId(),sysRole.getId());
        return appUser;
    }

    @Override
    public List<AppUser> findByNickName(String nickName) {
        return appUserDao.findByNickName(nickName);

    }

    @Transactional
    @Override
    public LoginAppUser findByPhone(String phone) {
        AppUser appUser = appUserDao.findUserByPhone(phone);
        return setUserRoles(appUser);
    }

    private LoginAppUser setUserRoles (AppUser appUser) {
        if (ComUtil.isEmpty(appUser)) {
            return null;
        }
        LoginAppUser loginAppUser = new LoginAppUser();
        BeanUtils.copyProperties(appUser, loginAppUser);

        Set<SysRole> sysRoles = userRoleDao.findRolesByUserId(appUser.getId());
        loginAppUser.setSysRoles(sysRoles);// 设置角色

        if (!CollectionUtils.isEmpty(sysRoles)) {
            Set<Long> roleIds = sysRoles.parallelStream().map(SysRole::getId).collect(Collectors.toSet());
            Set<SysPermission> sysPermissions = sysPermissionService.findByRoleIds(roleIds);
            if (!CollectionUtils.isEmpty(sysPermissions)) {
                Set<String> permissions = sysPermissions.parallelStream().map(SysPermission::getPermission)
                        .collect(Collectors.toSet());

                loginAppUser.setPermissions(permissions);// 设置权限集合
            }

        }

        return loginAppUser;
    }

}
