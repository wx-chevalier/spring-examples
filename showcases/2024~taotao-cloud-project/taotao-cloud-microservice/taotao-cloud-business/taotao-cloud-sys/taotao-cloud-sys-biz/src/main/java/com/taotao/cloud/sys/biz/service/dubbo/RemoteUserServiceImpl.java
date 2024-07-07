/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.sys.biz.service.dubbo;

import com.taotao.cloud.sys.api.dubbo.RemoteUserService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/** 用户服务 */
@RequiredArgsConstructor
@Service
@DubboService
public class RemoteUserServiceImpl implements RemoteUserService {

    // private final ISysUserService userService;
    // private final ISysPermissionService permissionService;
    // private final ISysConfigService configService;
    // private final SysUserMapper userMapper;
    //
    // @Override
    // public LoginUser getUserInfo(String username) throws UserException {
    //	SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
    //		.select(SysUser::getUserName, SysUser::getStatus)
    //		.eq(SysUser::getUserName, username));
    //	if (ObjectUtil.isNull(sysUser)) {
    //		throw new UserException("user.not.exists", username);
    //	}
    //	if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
    //		throw new UserException("user.blocked", username);
    //	}
    //	// 此处可根据登录用户的数据不同 自行创建 loginUser
    //	return buildLoginUser(userMapper.selectUserByUserName(username));
    // }
    //
    // @Override
    // public LoginUser getUserInfoByPhonenumber(String phonenumber) throws UserException {
    //	SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
    //		.select(SysUser::getPhonenumber, SysUser::getStatus)
    //		.eq(SysUser::getPhonenumber, phonenumber));
    //	if (ObjectUtil.isNull(sysUser)) {
    //		throw new UserException("user.not.exists", phonenumber);
    //	}
    //	if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
    //		throw new UserException("user.blocked", phonenumber);
    //	}
    //	// 此处可根据登录用户的数据不同 自行创建 loginUser
    //	return buildLoginUser(userMapper.selectUserByPhonenumber(phonenumber));
    // }
    //
    // @Override
    // public XcxLoginUser getUserInfoByOpenid(String openid) throws UserException {
    //	// todo 自行实现 userService.selectUserByOpenid(openid);
    //	SysUser sysUser = new SysUser();
    //	if (ObjectUtil.isNull(sysUser)) {
    //		// todo 用户不存在 业务逻辑自行实现
    //	}
    //	if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
    //		// todo 用户已被停用 业务逻辑自行实现
    //	}
    //	// 此处可根据登录用户的数据不同 自行创建 loginUser
    //	XcxLoginUser loginUser = new XcxLoginUser();
    //	loginUser.setUserId(sysUser.getUserId());
    //	loginUser.setUsername(sysUser.getUserName());
    //	loginUser.setUserType(sysUser.getUserType());
    //	loginUser.setOpenid(openid);
    //	return loginUser;
    // }
    //
    // @Override
    // public Boolean registerUserInfo(SysUser sysUser) {
    //	String username = sysUser.getUserName();
    //	if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
    //		throw new ServiceException("当前系统没有开启注册功能");
    //	}
    //	if (!userService.checkUserNameUnique(sysUser)) {
    //		throw new UserException("user.register.save.error", username);
    //	}
    //	return userService.registerUser(sysUser);
    // }
    //
    // @Override
    // public String selectUserNameById(Long userId) {
    //	return userService.selectUserNameById(userId);
    // }
    //
    /// **
    // * 构建登录用户
    // */
    // private LoginUser buildLoginUser(SysUser user) {
    //	LoginUser loginUser = new LoginUser();
    //	loginUser.setUserId(user.getUserId());
    //	loginUser.setDeptId(user.getDeptId());
    //	loginUser.setUsername(user.getUserName());
    //	loginUser.setPassword(user.getPassword());
    //	loginUser.setUserType(user.getUserType());
    //	loginUser.setMenuPermission(permissionService.getMenuPermission(user));
    //	loginUser.setRolePermission(permissionService.getRolePermission(user));
    //	loginUser.setDeptName(
    //		ObjectUtil.isNull(user.getDept()) ? "" : user.getDept().getDeptName());
    //	List<RoleDTO> roles = BeanUtil.copyToList(user.getRoles(), RoleDTO.class);
    //	loginUser.setRoles(roles);
    //	return loginUser;
    // }

}
