package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.utils.ComUtil;
import com.cloud.model.user.SysPermission;
import com.cloud.user.dao.RolePermissionDao;
import com.cloud.user.dao.SysPermissionDao;
import com.cloud.user.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {

	@Autowired
	private SysPermissionDao sysPermissionDao;
	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Override
	public Set<SysPermission> findByRoleIds(Set<Long> roleIds) {
		return rolePermissionDao.findPermissionsByRoleIds(roleIds);
	}

	@Transactional
	@Override
	public void saveSysPermission(SysPermission sysPermission) {
		SysPermission permission = this.selectOne(new EntityWrapper<SysPermission>().eq("permission",sysPermission.getPermission()));
		if (permission != null) {
			throw new IllegalArgumentException("权限标识已存在");
		}
		sysPermission.setCreateTime(System.currentTimeMillis());
		sysPermission.setUpdateTime(sysPermission.getCreateTime());

		sysPermissionDao.insert(sysPermission);
		log.info("保存权限标识：{}", sysPermission);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		SysPermission permission = sysPermissionDao.selectById(id);
		if (permission == null) {
			throw new IllegalArgumentException("权限标识不存在");
		}
		sysPermissionDao.deleteById(id);
		rolePermissionDao.deleteRolePermission(null, id);
		log.info("删除权限标识：{}", permission);
	}

	@Override
	public Page<SysPermission> findPermissionsByPage(Page<SysPermission> sysPermissionPage, Map<String, Object> params) {
		EntityWrapper<SysPermission> ew = new EntityWrapper();
		if(!ComUtil.isEmpty(params.get("permission"))){
			ew.like("permission",params.get("permission").toString());
		}
		if(!ComUtil.isEmpty(params.get("name"))){
			ew.like("name",params.get("name").toString());
		}
		return sysPermissionPage.setRecords(this.selectPage(sysPermissionPage,ew).getRecords());
	}
}
