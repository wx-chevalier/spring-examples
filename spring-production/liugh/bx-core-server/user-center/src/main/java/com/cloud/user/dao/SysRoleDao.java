package com.cloud.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.model.user.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {


	@Select("select * from sys_role t where t.code = #{code}")
	SysRole findByCode(String code);

}
