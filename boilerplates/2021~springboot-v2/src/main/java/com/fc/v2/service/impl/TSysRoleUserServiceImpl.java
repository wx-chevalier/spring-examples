
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.service.ITSysRoleUserService;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysRoleUserMapper;
import com.fc.v2.model.auto.TSysRole;
import com.fc.v2.model.auto.TSysRoleUser;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 用户角色中间Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysRoleUserServiceImpl  extends ServiceImpl<TSysRoleUserMapper, TSysRoleUser> implements ITSysRoleUserService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysRoleUserServiceImpl.class);

    @Autowired
    private TSysRoleUserMapper tSysRoleUserMapper;

    /**
     * 查询用户角色中间
     * 
     * @param id 用户角色中间ID
     * @return 用户角色中间
     */
    @Override
    public TSysRoleUser selectTSysRoleUserById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询用户角色中间列表
     * 
     * @param queryWrapper 用户角色中间
     * @return 用户角色中间
     */
    @Override
    public List<TSysRoleUser> selectTSysRoleUserList(Wrapper<TSysRoleUser> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<TSysRoleUser> selectTSysRoleUserList(TSysRoleUser tSysRoleUser) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysRoleUser, true, true);
    	QueryWrapper<TSysRoleUser> queryWrapper = new QueryWrapper<TSysRoleUser>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增用户角色中间
     * 
     * @param tSysRoleUser 用户角色中间
     * @return 结果
     */
    @Override
    public int insertTSysRoleUser(TSysRoleUser tSysRoleUser)
    {
        return this.baseMapper.insert(tSysRoleUser);
    }

    /**
     * 新增用户角色中间
     *
     * @param tSysRoleUsers 批量保存用户角色中间
     * @return 结果
     */
    @Override
    public boolean saveTSysRoleUserBatch(List<TSysRoleUser> tSysRoleUsers) {
        return this.saveBatch(tSysRoleUsers);
    }

    /**
     * 修改用户角色中间
     * 
     * @param tSysRoleUser 用户角色中间
     * @return 结果
     */
    @Override
    public int updateTSysRoleUser(TSysRoleUser tSysRoleUser)
    {
        return this.baseMapper.updateById(tSysRoleUser);
    }

    /**
     * 删除用户角色中间对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysRoleUserByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除用户角色中间信息
     * 
     * @param id 用户角色中间ID
     * @return 结果
     */
    @Override
    public int deleteTSysRoleUserById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 删除用户角色中间信息
     *
     * @param queryWrapper
     * @return 结果
     */
    @Override
    public int deleteTSysRoleUser(QueryWrapper<TSysRoleUser> queryWrapper) {
        return this.baseMapper.delete(queryWrapper);
    }
}
