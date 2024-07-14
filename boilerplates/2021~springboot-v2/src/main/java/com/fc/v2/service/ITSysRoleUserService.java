
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysRoleUser;

/**
 * 用户角色中间Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysRoleUserService extends IService<TSysRoleUser> {
	/**
	 * 查询用户角色中间
	 * 
	 * @param id 用户角色中间ID
	 * @return 用户角色中间
	 */
	public TSysRoleUser selectTSysRoleUserById(Long id);

	/**
	 * 查询用户角色中间列表
	 * 
	 * @param queryWrapper 用户角色中间
	 * @return 用户角色中间集合
	 */
	public List<TSysRoleUser> selectTSysRoleUserList(Wrapper<TSysRoleUser> queryWrapper);

	/**
	 * 查询用户角色中间列表
	 *
	 * @param tSysRoleUser 用户角色中间
	 * @return 用户角色中间集合
	 */
	public List<TSysRoleUser> selectTSysRoleUserList(TSysRoleUser tSysRoleUser);

	/**
	 * 新增用户角色中间
	 * 
	 * @param tSysRoleUser 用户角色中间
	 * @return 结果
	 */
	public int insertTSysRoleUser(TSysRoleUser tSysRoleUser);

	/**
	 * 新增用户角色中间
	 *
	 * @param tSysRoleUsers 批量保存用户角色中间
	 * @return 结果
	 */
	public boolean saveTSysRoleUserBatch(List<TSysRoleUser> tSysRoleUsers);
	/**
	 * 修改用户角色中间
	 * 
	 * @param tSysRoleUser 用户角色中间
	 * @return 结果
	 */
	public int updateTSysRoleUser(TSysRoleUser tSysRoleUser);

	/**
	 * 批量删除用户角色中间
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysRoleUserByIds(String ids);

	/**
	 * 删除用户角色中间信息
	 * 
	 * @param id 用户角色中间ID
	 * @return 结果
	 */
	public int deleteTSysRoleUserById(Long id);

	/**
	 * 删除用户角色中间信息
	 *
	 * @param queryWrapper
	 * @return 结果
	 */
	public int deleteTSysRoleUser(QueryWrapper<TSysRoleUser> queryWrapper);
}
