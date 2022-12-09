package com.cloud.user.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.user.AppUser;
import com.cloud.model.user.UserCredential;

@Mapper
public interface UserCredentialsDao {

	@Delete("delete from user_credentials  where  userId = #{userId}")
	int deleteByUserId(String userId);

	@Insert("insert into user_credentials(username, type, userId) values(#{username}, #{type}, #{userId})")
	int save(UserCredential userCredential);

	@Select("select * from user_credentials t where t.username = #{username}")
	UserCredential findByUsername(String username);

}
