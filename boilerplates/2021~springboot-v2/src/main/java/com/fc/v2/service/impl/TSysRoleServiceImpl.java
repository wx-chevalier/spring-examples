
package com.fc.v2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.model.auto.TSysPermissionRole;
import com.fc.v2.service.ITSysPermissionRoleService;
import com.fc.v2.service.ITSysRoleService;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysRoleMapper;
import com.fc.v2.model.auto.TSysRole;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 角色Service业务层处理
 *
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysRoleServiceImpl extends ServiceImpl<TSysRoleMapper, TSysRole> implements ITSysRoleService {

    private static final Logger logger = LoggerFactory.getLogger(TSysRoleServiceImpl.class);

    @Autowired
    private TSysRoleMapper tSysRoleMapper;

    @Autowired
    private ITSysPermissionRoleService permissionRoleService;

    /**
     * 查询角色
     *
     * @param id 角色ID
     * @return 角色
     */
    @Override
    public TSysRole selectTSysRoleById(Long id) {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询角色列表
     *
     * @param queryWrapper 角色
     * @return 角色
     */
    @Override
    public List<TSysRole> selectTSysRoleList(Wrapper<TSysRole> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询角色列表
     *
     * @param tSysRole 角色
     * @return 角色集合
     */
    @Override
    public List<TSysRole> selectTSysRoleList(TSysRole tSysRole) {
        Map<String, Object> map = BeanUtil.beanToMap(tSysRole, true, true);
        QueryWrapper<TSysRole> queryWrapper = new QueryWrapper<TSysRole>();
        queryWrapper.allEq(map, false);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增角色
     *
     * @param tSysRole 角色
     * @return 结果
     */
    @Override
    public int insertTSysRole(TSysRole tSysRole) {
        return this.baseMapper.insert(tSysRole);
    }

    /**
     * 修改角色
     *
     * @param tSysRole 角色
     * @return 结果
     */
    @Override
    public int updateTSysRole(TSysRole tSysRole) {
        return this.baseMapper.updateById(tSysRole);
    }

    /**
     * 删除角色对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysRoleByIds(String ids) {
        QueryWrapper<TSysPermissionRole> queryWrapper = new QueryWrapper<TSysPermissionRole>();
        queryWrapper.in("role_id", Arrays.asList(ConvertUtil.toStrArray(ids)));
        permissionRoleService.deleteTSysPermissionRole(queryWrapper);
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除角色信息
     *
     * @param id 角色ID
     * @return 结果
     */
    @Override
    public int deleteTSysRoleById(Long id) {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 根据用户id查询角色
     *
     * @param userid
     * @return
     */
    @Override
    public List<TSysRole> queryUserRole(Long userid) {
        return tSysRoleMapper.queryUserRole(userid);
    }

    /**
     * @param tsysRole
     * @return
     */
    @Override
    public int checkNameUnique(TSysRole tsysRole) {
        QueryWrapper<TSysRole> queryWrapper = new QueryWrapper<TSysRole>();
        queryWrapper.eq("name", tsysRole.getName());
        return this.baseMapper.selectList(queryWrapper).size();
    }

    /**
     * 修改用户角色 以及下面绑定的权限
     *
     * @param roleId
     * @param powerIds
     * @return
     */
    @Override
    public int updateRoleAndPrem(Long roleId, String powerIds) {
        //先删除角色下面的所有权限
        QueryWrapper<TSysPermissionRole> queryWrapper = new QueryWrapper<TSysPermissionRole>();
        queryWrapper.eq("role_id", roleId);

        permissionRoleService.deleteTSysPermissionRole(queryWrapper);
        List<TSysPermissionRole> list = new ArrayList<TSysPermissionRole>();
        //添加权限关联
        List<String> prems = ConvertUtil.toListStrArray(powerIds);

        prems.forEach(pre -> {
            TSysPermissionRole permissionRole = new TSysPermissionRole();
            permissionRole.setRoleId(roleId);
            permissionRole.setPermissionId(Long.parseLong(pre));
            list.add(permissionRole);
        });

        return permissionRoleService.saveTSysPermissionRoleBatch(list) ? 1 : 0;
    }
}
