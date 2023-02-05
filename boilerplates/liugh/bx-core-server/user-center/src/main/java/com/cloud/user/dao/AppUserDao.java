package com.cloud.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.model.user.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppUserDao  extends BaseMapper<AppUser> {

	List<AppUser> findUsersByPage(Page<AppUser> appUserPage, Map<String, Object> params);

	AppUser findUserByUsername(@Param("username") String username);

	List<AppUser> findByNickName(String nickName);

	AppUser findUserByPhone(@Param("phone") String phone);
}
