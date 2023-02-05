
package com.fc.v2.mapper.auto;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.v2.model.auto.TSysUser;

/**
 * 用户Mapper接口
 *
 * @author zhaonz
 * @date 2021-08-05
 */
public interface TSysUserMapper extends BaseMapper<TSysUser> {

	/**
	 * 查询用户
	 *
	 * @param id
	 * @return
	 */
	public TSysUser selectTSysUserById(Long id);

	/**
	 * 查询用户列表
	 *
	 * @param tSysUser
	 * @return
	 */
	public List<TSysUser> selectTSysUserList(TSysUser tSysUser);

	/**
	 * 查询用户列表
	 *
	 * @param depId
	 * @return
	 */
	public List<TSysUser> selectTSysUserByDepIdList(String[] depId);
}