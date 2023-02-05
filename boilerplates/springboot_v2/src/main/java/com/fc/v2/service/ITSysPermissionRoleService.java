
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysPermissionRole;
import org.apache.ibatis.annotations.Param;

/**
 * 角色权限中间Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysPermissionRoleService extends IService<TSysPermissionRole> {
	/**
	 * 查询角色权限中间
	 * 
	 * @param id 角色权限中间ID
	 * @return 角色权限中间
	 */
	public TSysPermissionRole selectTSysPermissionRoleById(Long id);

	/**
	 * 查询角色权限中间列表
	 * 
	 * @param queryWrapper 角色权限中间
	 * @return 角色权限中间集合
	 */
	public List<TSysPermissionRole> selectTSysPermissionRoleList(Wrapper<TSysPermissionRole> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询总记录数
	 *
	 * @param queryWrapper
	 * @return
	 */
	Integer selectCount(@Param(Constants.WRAPPER) Wrapper<TSysPermissionRole> queryWrapper);

	/**
	 * 查询角色权限中间列表
	 *
	 * @param tSysPermissionRole 角色权限中间
	 * @return 角色权限中间集合
	 */
	public List<TSysPermissionRole> selectTSysPermissionRoleList(TSysPermissionRole tSysPermissionRole);

	/**
	 * 新增角色权限中间
	 * 
	 * @param tSysPermissionRole 角色权限中间
	 * @return 结果
	 */
	public int insertTSysPermissionRole(TSysPermissionRole tSysPermissionRole);

	/**
	 * 批量新增角色权限中间
	 *
	 * @param tSysPermissionRole
	 * @return
	 */
	public boolean saveTSysPermissionRoleBatch(List<TSysPermissionRole> tSysPermissionRole);

	/**
	 * 修改角色权限中间
	 * 
	 * @param tSysPermissionRole 角色权限中间
	 * @return 结果
	 */
	public int updateTSysPermissionRole(TSysPermissionRole tSysPermissionRole);

	/**
	 * 批量删除角色权限中间
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysPermissionRoleByIds(String ids);

	/**
	 * 删除角色权限中间信息
	 * 
	 * @param id 角色权限中间ID
	 * @return 结果
	 */
	public int deleteTSysPermissionRoleById(Long id);

	/**
	 * 删除角色权限中间信息
	 *
	 * @param queryWrapper
	 * @return 结果
	 */
	public int deleteTSysPermissionRole(QueryWrapper<TSysPermissionRole> queryWrapper);
}
