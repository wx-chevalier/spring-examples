
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysUser;
import com.fc.v2.model.custom.RoleVo;
import com.fc.v2.model.custom.Tablepar;
import com.github.pagehelper.PageInfo;

/**
 * 用户Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysUserService extends IService<TSysUser> {
	/**
	 * 查询用户
	 * 
	 * @param id 用户ID
	 * @return 用户
	 */
	public TSysUser selectTSysUserById(Long id);

	/**
	 * 查询用户列表
	 * 
	 * @param queryWrapper 用户
	 * @return 用户集合
	 */
	public List<TSysUser> selectTSysUserList(Wrapper<TSysUser> queryWrapper);

	/**
	 * 查询用户列表
	 *
	 * @param tSysUser 用户
	 * @return 用户集合
	 */
	public List<TSysUser> selectTSysUserList(TSysUser tSysUser);

	/**
	 * 新增用户
	 * 
	 * @param TSysUser 用户
	 * @return 结果
	 */
	public int insertTSysUser(TSysUser TSysUser);

	/**
	 * 修改用户
	 * 
	 * @param TSysUser 用户
	 * @return 结果
	 */
	public int updateTSysUser(TSysUser TSysUser);

	/**
	 * 批量删除用户
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysUserByIds(String ids);

	/**
	 * 删除用户信息
	 * 
	 * @param id 用户ID
	 * @return 结果
	 */
	public int deleteTSysUserById(Long id);

	/**
	 * 新增用户
	 *
	 * @param user
	 * @param roleIds
	 * @return
	 */
	int insertUserRoles(TSysUser user, String roleIds);

	/**
	 * 检查LogingName
	 *
	 * @param tsysUser
	 * @return
	 */
	int checkLoginNameUnique(TSysUser tsysUser);

	/**
	 * 获取所有权限 并且增加是否有权限字段
	 *
	 * @param id
	 * @return
	 */
	List<RoleVo> getUserIsRole(Long id);

	/**
	 * 修改用户信息以及角色信息
	 *
	 * @param tsysUser
	 * @param roleIds
	 * @return
	 */
	int updateUserRoles(TSysUser tsysUser, String roleIds);

	/**
	 * 修改用户密码
	 *
	 * @param tsysUser
	 * @return
	 */
	int updateUserPassword(TSysUser tsysUser);

	/**
	 * 查询用户列表
	 *
	 * @param depIds
	 * @return
	 */
	public List<TSysUser> selectTSysUserByDepIdList(String depIds);
}
