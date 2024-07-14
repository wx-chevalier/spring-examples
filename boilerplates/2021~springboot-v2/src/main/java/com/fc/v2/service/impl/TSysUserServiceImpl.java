
package com.fc.v2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.model.auto.TSysRole;
import com.fc.v2.model.custom.RoleVo;
import com.fc.v2.service.ITSysRoleService;
import com.fc.v2.service.ITSysRoleUserService;
import com.fc.v2.service.ITSysUserService;
import com.fc.v2.util.MD5Util;
import com.fc.v2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysUserMapper;
import com.fc.v2.model.auto.TSysRoleUser;
import com.fc.v2.model.auto.TSysUser;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户Service业务层处理
 *
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysUserServiceImpl extends ServiceImpl<TSysUserMapper, TSysUser> implements ITSysUserService {

    private static final Logger logger = LoggerFactory.getLogger(TSysUserServiceImpl.class);

    @Autowired
    private TSysUserMapper tSysUserMapper;

    @Autowired
    private ITSysRoleUserService itSysRoleUserService;

    @Autowired
    private ITSysRoleService itSysRoleService;

    /**
     * 查询用户
     *
     * @param id 用户ID
     * @return 用户
     */
    @Override
    public TSysUser selectTSysUserById(Long id) {
        return tSysUserMapper.selectTSysUserById(id);
    }

    /**
     * 查询用户列表
     *
     * @param queryWrapper 用户
     * @return 用户
     */
    @Override
    public List<TSysUser> selectTSysUserList(Wrapper<TSysUser> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询用户列表
     *
     * @param tSysUser 用户
     * @return 用户
     */
    @Override
    public List<TSysUser> selectTSysUserList(TSysUser tSysUser) {
        return tSysUserMapper.selectTSysUserList(tSysUser);
    }

    /**
     * 新增用户
     *
     * @param tSysUser 用户
     * @return 结果
     */
    @Override
    public int insertTSysUser(TSysUser tSysUser) {
        return this.baseMapper.insert(tSysUser);
    }

    /**
     * 修改用户
     *
     * @param tSysUser 用户
     * @return 结果
     */
    @Override
    public int updateTSysUser(TSysUser tSysUser) {
        return this.baseMapper.updateById(tSysUser);
    }

    /**
     * 删除用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysUserByIds(String ids) {
        QueryWrapper<TSysRoleUser> queryWrapper = new QueryWrapper<TSysRoleUser>();
        queryWrapper.in("sys_user_id", Arrays.asList(ConvertUtil.toStrArray(ids)));
        itSysRoleUserService.deleteTSysRoleUser(queryWrapper);
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除用户信息
     *
     * @param id 用户ID
     * @return 结果
     */
    @Override
    public int deleteTSysUserById(Long id) {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 新增用户
     *
     * @param user
     * @param roleIds
     * @return
     */
    @Override
    public int insertUserRoles(TSysUser user, String roleIds) {
        user.setId(IdWorker.getId());
        if (StringUtils.isNotEmpty(roleIds)) {
            List<String> listRoles = ConvertUtil.toListStrArray(roleIds);
            listRoles.forEach(s -> {
                TSysRoleUser tSysRoleUser = new TSysRoleUser();
                tSysRoleUser.seTSysUserId(user.getId());
                tSysRoleUser.setSysRoleId(Long.parseLong(s));
                itSysRoleUserService.insertTSysRoleUser(tSysRoleUser);
            });
        }

        user.setPassword(MD5Util.encode(user.getPassword()));
        return this.baseMapper.insert(user);
    }

    /**
     * 检查LogingName
     *
     * @param tsysUser
     * @return
     */
    @Override
    public int checkLoginNameUnique(TSysUser tsysUser) {
        QueryWrapper<TSysUser> queryWrapper = new QueryWrapper<TSysUser>();
        queryWrapper.eq("username", tsysUser.getUsername());
        return this.baseMapper.selectList(queryWrapper).size();
    }

    /**
     * 获取所有权限 并且增加是否有权限字段
     *
     * @param id
     * @return
     */
    @Override
    public List<RoleVo> getUserIsRole(Long id) {
        List<RoleVo> list = new ArrayList<RoleVo>();
        //查询出我的权限
        List<TSysRole> myRoles = itSysRoleService.queryUserRole(id);
        //查询系统所有的角色
        List<TSysRole> tsysRoles = itSysRoleService.selectTSysRoleList(new QueryWrapper<TSysRole>());
        if (StringUtils.isNotEmpty(tsysRoles)) {
            for (TSysRole tsysRole : tsysRoles) {
                Boolean isflag = false;
                RoleVo roleVo = new RoleVo(tsysRole.getId(), tsysRole.getName(), isflag);
                for (TSysRole myRole : myRoles) {
                    if (tsysRole.getId().equals(myRole.getId())) {
                        isflag = true;
                        break;
                    }
                }
                if (isflag) {
                    roleVo.setIscheck(true);
                    list.add(roleVo);
                } else {
                    list.add(roleVo);
                }
            }
        }
        return list;
    }

    /**
     * 修改用户信息以及角色信息
     *
     * @param tsysUser
     * @param roleIds
     * @return
     */
    @Override
    public int updateUserRoles(TSysUser tsysUser, String roleIds) {
        //先删除这个用户的所有角色
        QueryWrapper<TSysRoleUser> queryWrapper = new QueryWrapper<TSysRoleUser>();
        queryWrapper.eq("sys_user_id", tsysUser.getId());
        itSysRoleUserService.deleteTSysRoleUser(queryWrapper);

        if (StringUtils.isNotEmpty(roleIds)) {
            List<String> listRoles = ConvertUtil.toListStrArray(roleIds);
            //添加新的角色信息
            listRoles.forEach(s -> {
                TSysRoleUser tSysRoleUser = new TSysRoleUser();
                tSysRoleUser.seTSysUserId(tsysUser.getId());
                tSysRoleUser.setSysRoleId(Long.parseLong(s));
                itSysRoleUserService.insertTSysRoleUser(tSysRoleUser);
            });
        }
        //修改用户信息
        return this.updateTSysUser(tsysUser);
    }

    @Override
    public int updateUserPassword(TSysUser tsysUser) {
        tsysUser.setPassword(MD5Util.encode(tsysUser.getPassword()));
        return this.updateTSysUser(tsysUser);
    }

    /**
     * 查询用户列表
     *
     * @param depIds
     * @return
     */
    @Override
    public List<TSysUser> selectTSysUserByDepIdList(String depIds) {
        return tSysUserMapper.selectTSysUserByDepIdList(ConvertUtil.toStrArray(depIds));
    }
}
