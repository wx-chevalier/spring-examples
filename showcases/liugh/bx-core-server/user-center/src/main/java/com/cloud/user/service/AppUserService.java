package com.cloud.user.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AppUserService extends IService<AppUser> {

	AppUser addAppUser(AppUser appUser)throws Exception;

	AppUser updateAppUser(AppUser appUser)throws Exception;

	LoginAppUser findByUsername(String username);

	void setRoleToUser(String id, Set<Long> roleIds)throws Exception;

	AppUser updatePassword(String id, String oldPassword, String newPassword)throws Exception;


	Set<SysRole> findRolesByUserId(String userId);

	void bindingPhone(String userId, String phone)throws Exception;

	Page<AppUser> findUsersByPage(Page<AppUser> appUserPage, Map<String, Object> params);

	SysRole findRoleByUserId(String id);

	AppUser deleteUser(AppUser appUser)throws Exception;

	List<AppUser> findByNickName(String nickName);

	LoginAppUser findByPhone(String phone);
}
