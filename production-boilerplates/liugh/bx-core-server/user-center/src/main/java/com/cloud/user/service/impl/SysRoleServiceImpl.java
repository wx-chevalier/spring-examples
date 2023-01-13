package com.cloud.user.service.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.utils.ComUtil;
import com.cloud.model.user.constants.RoleType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.cloud.model.user.SysPermission;
import com.cloud.model.user.SysRole;
import com.cloud.user.dao.RolePermissionDao;
import com.cloud.user.dao.SysRoleDao;
import com.cloud.user.dao.UserRoleDao;
import com.cloud.user.service.SysRoleService;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RolePermissionDao rolePermissionDao;
//	@Autowired
//	private AmqpTemplate amqpTemplate;

	@Transactional
	@Override
	public void saveSysRole(SysRole sysRole) {
		if(this.isAdmin(sysRole.getCode())){
			throw new IllegalArgumentException("不能操作管理员");
		}
		if(!ComUtil.isEmpty(sysRole.getId())){
			if(StringUtils.isBlank(sysRole.getName())) {
				throw new IllegalArgumentException("角色名不能为空");
			}
			sysRole.setUpdateTime(System.currentTimeMillis());
			this.updateById(sysRole);
		}else {
			SysRole role = this.selectOne(new EntityWrapper<SysRole>().eq("code", sysRole.getCode()));
			if (!ComUtil.isEmpty(role)) {
				throw new IllegalArgumentException("角色code已存在");
			}
			if (!ComUtil.isEmpty(this.selectOne(new EntityWrapper<SysRole>().eq("name", sysRole.getName())))) {
				throw new IllegalArgumentException("角色name已存在");
			}
			sysRole.setCreateTime(System.currentTimeMillis());
			sysRole.setUpdateTime(sysRole.getCreateTime());
			sysRoleDao.insert(sysRole);
		}
		log.info("保存角色：{}", sysRole);
	}

	private boolean isAdmin(String roleName){
		if (!ComUtil.isEmpty(roleName)){
			if(RoleType.SUPER_ADMIN.name().equals(roleName)){
				return true;
			}
		}
		return false;
	}


	@Transactional
	@Override
	public SysRole deleteRole(Long id) {
		SysRole sysRole = sysRoleDao.selectById(id);
		if(this.isAdmin(sysRole.getCode())){
			throw new IllegalArgumentException("不能操作管理员");
		}
		if(!ComUtil.isEmpty(sysRole)){
			sysRoleDao.deleteById(id);
			rolePermissionDao.deleteRolePermission(id, null);
			userRoleDao.deleteUserRole(null, id);
			log.info("删除角色：{}", sysRole);

			// 发布role删除的消息
//			amqpTemplate.convertAndSend(UserCenterMq.MQ_EXCHANGE_USER, UserCenterMq.ROUTING_KEY_ROLE_DELETE, id);

		}
		return sysRole;
	}

	/**
	 * 给角色设置权限
	 *
	 * @param roleId
	 * @param permissionIds
	 */
	@Transactional
	@Override
	public void setPermissionToRole(Long roleId, Set<Long> permissionIds) {
		SysRole sysRole = sysRoleDao.selectById(roleId);
		if (sysRole == null) {
			throw new IllegalArgumentException("角色不存在");
		}
		if(this.isAdmin(sysRole.getCode())){
			throw new IllegalArgumentException("不能操作管理员");
		}

		// 查出角色对应的old权限
		Set<Long> oldPermissionIds = rolePermissionDao.findPermissionsByRoleIds(Sets.newHashSet(roleId)).stream()
				.map(p -> p.getId()).collect(Collectors.toSet());

		// 需要添加的权限
		Collection<Long> addPermissionIds = org.apache.commons.collections4.CollectionUtils.subtract(permissionIds,
				oldPermissionIds);
		if (!CollectionUtils.isEmpty(addPermissionIds)) {
			addPermissionIds.forEach(permissionId -> {
				rolePermissionDao.saveRolePermission(roleId, permissionId);
			});
		}
		// 需要移除的权限
		Collection<Long> deletePermissionIds = org.apache.commons.collections4.CollectionUtils
				.subtract(oldPermissionIds, permissionIds);
		if (!CollectionUtils.isEmpty(deletePermissionIds)) {
			deletePermissionIds.forEach(permissionId -> {
				rolePermissionDao.deleteRolePermission(roleId, permissionId);
			});
		}

		log.info("给角色id：{}，分配权限：{}", roleId, permissionIds);
	}


	@Override
	public Set<SysPermission> findPermissionsByRoleId(Long roleId) {
		return rolePermissionDao.findPermissionsByRoleIds(Sets.newHashSet(roleId));
	}

	@Override
	public Page<SysRole> findRolesByPage(Page<SysRole> sysRolePage, String name) {
		EntityWrapper<SysRole> ew = new EntityWrapper();
		if(!ComUtil.isEmpty(name)){
			ew.like("name",name);
		}
		return sysRolePage.setRecords(this.selectPage(sysRolePage,ew).getRecords());
	}
}
