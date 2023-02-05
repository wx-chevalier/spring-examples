
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.service.ITSysPermissionRoleService;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysPermissionRoleMapper;
import com.fc.v2.model.auto.TSysOperLog;
import com.fc.v2.model.auto.TSysPermissionRole;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 角色权限中间Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysPermissionRoleServiceImpl  extends ServiceImpl<TSysPermissionRoleMapper, TSysPermissionRole> implements ITSysPermissionRoleService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysPermissionRoleServiceImpl.class);

    @Autowired
    private TSysPermissionRoleMapper tSysPermissionRoleMapper;

    /**
     * 查询角色权限中间
     * 
     * @param id 角色权限中间ID
     * @return 角色权限中间
     */
    @Override
    public TSysPermissionRole selectTSysPermissionRoleById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询角色权限中间列表
     * 
     * @param queryWrapper 角色权限中间
     * @return 角色权限中间
     */
    @Override
    public List<TSysPermissionRole> selectTSysPermissionRoleList(Wrapper<TSysPermissionRole> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询角色权限中间列表
     *
     * @param tSysPermissionRole 角色权限中间
     * @return 角色权限中间
     */
    @Override
    public List<TSysPermissionRole> selectTSysPermissionRoleList(TSysPermissionRole tSysPermissionRole) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysPermissionRole, true, true);
    	QueryWrapper<TSysPermissionRole> queryWrapper = new QueryWrapper<TSysPermissionRole>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper
     * @return
     */
    @Override
    public Integer selectCount(Wrapper<TSysPermissionRole> queryWrapper) {
        return this.baseMapper.selectCount(queryWrapper);
    }

    /**
     * 新增角色权限中间
     * 
     * @param tSysPermissionRole 角色权限中间
     * @return 结果
     */
    @Override
    public int insertTSysPermissionRole(TSysPermissionRole tSysPermissionRole)
    {
        return this.baseMapper.insert(tSysPermissionRole);
    }

    /**
     * 批量新增角色权限中间
     *
     * @param tSysPermissionRole
     * @return
     */
    @Override
    public boolean saveTSysPermissionRoleBatch(List<TSysPermissionRole> tSysPermissionRole) {
        return this.saveBatch(tSysPermissionRole);
    }

    /**
     * 修改角色权限中间
     * 
     * @param tSysPermissionRole 角色权限中间
     * @return 结果
     */
    @Override
    public int updateTSysPermissionRole(TSysPermissionRole tSysPermissionRole)
    {
        return this.baseMapper.updateById(tSysPermissionRole);
    }

    /**
     * 删除角色权限中间对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysPermissionRoleByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除角色权限中间信息
     * 
     * @param id 角色权限中间ID
     * @return 结果
     */
    @Override
    public int deleteTSysPermissionRoleById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 删除角色权限中间信息
     *
     * @param queryWrapper
     * @return 结果
     */
    @Override
    public int deleteTSysPermissionRole(QueryWrapper<TSysPermissionRole> queryWrapper) {
        return this.baseMapper.delete(queryWrapper);
    }
}
