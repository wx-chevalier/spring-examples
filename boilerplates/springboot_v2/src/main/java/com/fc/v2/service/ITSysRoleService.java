
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysRole;

/**
 * 角色Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysRoleService extends IService<TSysRole> {
	/**
	 * 查询角色
	 * 
	 * @param id 角色ID
	 * @return 角色
	 */
	public TSysRole selectTSysRoleById(Long id);

	/**
	 * 查询角色列表
	 * 
	 * @param queryWrapper 角色
	 * @return 角色集合
	 */
	public List<TSysRole> selectTSysRoleList(Wrapper<TSysRole> queryWrapper);

	/**
	 * 查询角色列表
	 *
	 * @param tSysRole 角色
	 * @return 角色集合
	 */
	public List<TSysRole> selectTSysRoleList(TSysRole tSysRole);

	/**
	 * 新增角色
	 * 
	 * @param tSysRole 角色
	 * @return 结果
	 */
	public int insertTSysRole(TSysRole tSysRole);

	/**
	 * 修改角色
	 * 
	 * @param tSysRole 角色
	 * @return 结果
	 */
	public int updateTSysRole(TSysRole tSysRole);

	/**
	 * 批量删除角色
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysRoleByIds(String ids);

	/**
	 * 删除角色信息
	 * 
	 * @param id 角色ID
	 * @return 结果
	 */
	public int deleteTSysRoleById(Long id);

	/**
	 * 根据用户id查询角色
	 * 
	 * @param userid
	 * @return
	 */
	public List<TSysRole> queryUserRole(Long userid);

	/**
	 * 检查角色name
	 *
	 * @param tsysRole
	 * @return
	 */
	int checkNameUnique(TSysRole tsysRole);

	/**
	 * 修改用户角色 以及下面绑定的权限
	 *
	 * @param roleId
	 * @param powerIds
	 * @return
	 */
	int updateRoleAndPrem(Long roleId, String powerIds);
}
