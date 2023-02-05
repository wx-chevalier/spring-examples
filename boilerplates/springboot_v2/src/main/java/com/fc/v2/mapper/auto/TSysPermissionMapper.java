
package com.fc.v2.mapper.auto;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.v2.model.auto.TSysPermission;
import com.fc.v2.model.custom.SysMenu;

/**
 * 权限Mapper接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface TSysPermissionMapper extends BaseMapper<TSysPermission> {

	/**
	 * 查询全部权限
	 * 
	 * @return
	 */
	public List<TSysPermission> findAll();

	/**
	 * 根据用户id查询出用户的所有权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<TSysPermission> findByAdminUserId(Long userId);

	/**
	 * 根据角色id查询权限
	 * 
	 * @param roleid
	 * @return
	 */
	public List<TSysPermission> queryRoleId(Long roleid);

	/**
	 * 根据用户id查询菜单
	 *
	 * @param userId
	 * @return
	 */
	public List<SysMenu> findAllByIdMenu(Long userId);
}