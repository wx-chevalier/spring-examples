
package com.fc.v2.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.model.auto.TSysPermissionRole;
import com.fc.v2.model.custom.SysMenu;
import com.fc.v2.model.custom.SysPower;
import com.fc.v2.service.ITSysPermissionRoleService;
import com.fc.v2.service.ITSysPermissionService;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysPermissionMapper;
import com.fc.v2.model.auto.TSysPermission;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 权限Service业务层处理
 *
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysPermissionServiceImpl extends ServiceImpl<TSysPermissionMapper, TSysPermission> implements ITSysPermissionService {

    private static final Logger logger = LoggerFactory.getLogger(TSysPermissionServiceImpl.class);

    @Autowired
    private TSysPermissionMapper tSysPermissionMapper;

    @Autowired
    private ITSysPermissionRoleService itSysPermissionRoleService;

    /**
     * 查询权限
     *
     * @param id 权限ID
     * @return 权限
     */
    @Override
    public TSysPermission selectTSysPermissionById(Long id) {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询权限
     *
     * @param ids 权限ID
     * @return 权限
     */
    @Override
    public List<TSysPermission> selectTSysPermissionByIds(List<Long> ids) {
        return this.baseMapper.selectBatchIds(ids);
    }

    /**
     * 查询权限列表
     *
     * @param queryWrapper 权限
     * @return 权限
     */
    @Override
    public List<TSysPermission> selectTSysPermissionList(Wrapper<TSysPermission> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询权限列表
     *
     * @param tSysPermission 权限
     * @return
     */
    @Override
    public List<TSysPermission> selectTSysPermissionList(TSysPermission tSysPermission) {
        Map<String, Object> map = BeanUtil.beanToMap(tSysPermission, true, true);
        QueryWrapper<TSysPermission> queryWrapper = new QueryWrapper<TSysPermission>();
        queryWrapper.allEq(map, false);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增权限
     *
     * @param tSysPermission 权限
     * @return 结果
     */
    @Override
    public int insertTSysPermission(TSysPermission tSysPermission) {
        //默认设置不跳转
        if (tSysPermission.getIsBlank() == null) {
            tSysPermission.setIsBlank(0);
        }
        return this.baseMapper.insert(tSysPermission);
    }

    /**
     * 修改权限
     *
     * @param tSysPermission 权限
     * @return 结果
     */
    @Override
    public int updateTSysPermission(TSysPermission tSysPermission) {
        //默认设置不跳转
        if (tSysPermission.getIsBlank() == null) {
            tSysPermission.setIsBlank(0);
        }
        return this.baseMapper.updateById(tSysPermission);
    }

    /**
     * 删除权限对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public ImmutablePair<Integer, String> deleteTSysPermissionByIds(String ids){
        //转成集合
        List<String> lista = ConvertUtil.toListStrArray(ids);
        List<Long> cdids = lista.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());

        //判断角色是否删除去除
        QueryWrapper<TSysPermissionRole> queryWrapper = new QueryWrapper<TSysPermissionRole>();
        queryWrapper.in("permission_id", Arrays.asList(ConvertUtil.toStrArray(ids)));

        if (itSysPermissionRoleService.selectCount(queryWrapper) > 0){
            return ImmutablePair.of(-1, "该权限绑定了角色，请解除角色绑定");
        }

        //判断是否有子集
        QueryWrapper<TSysPermission> sysPermissionQueryWrapper = new QueryWrapper<TSysPermission>();
        sysPermissionQueryWrapper.in("pid", Arrays.asList(ConvertUtil.toStrArray(ids)));
        if (this.baseMapper.selectCount(sysPermissionQueryWrapper) > 0){
            return ImmutablePair.of(-1, "该权限有子权限，请先删除子权限");
        }

        return ImmutablePair.of(this.baseMapper.deleteBatchIds(cdids),null);
    }

    /**
     * 删除权限信息
     *
     * @param id 权限ID
     * @return 结果
     */
    @Override
    public int deleteTSysPermissionById(Long id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int checkNameUnique(TSysPermission tsysPermission) {
        TSysPermission sysPermission = new TSysPermission();
        sysPermission.setName(tsysPermission.getName());
        return this.selectTSysPermissionList(sysPermission).size();
    }

    @Override
    public int checkURLUnique(TSysPermission tsysPermission) {
        TSysPermission sysPermission = new TSysPermission();
        sysPermission.setName(tsysPermission.getUrl());
        return this.selectTSysPermissionList(sysPermission).size();
    }

    @Override
    public int checkPermsUnique(TSysPermission tsysPermission) {
        TSysPermission sysPermission = new TSysPermission();
        sysPermission.setName(tsysPermission.getPerms());
        return this.selectTSysPermissionList(sysPermission).size();
    }

    /**
     * 根据父id 以及类型查询权限子集
     *
     * @param pid
     * @return
     */
    public List<TSysPermission> queryPid(Long pid, int type) {
        TSysPermission sysPermission = new TSysPermission();
        sysPermission.setPid(pid);
        sysPermission.setType(type);
        return this.selectTSysPermissionList(sysPermission);
    }

    /**
     * @param roleId
     * @return
     */
    @Override
    public List<SysPower> getRolePower(Long roleId) {
        //所有权限
        List<TSysPermission> allPower = getall(null);
        //角色权限
        List<TSysPermission> rolePower = this.tSysPermissionMapper.queryRoleId(roleId);

        List<SysPower> sysPowerList = new ArrayList<SysPower>();

        allPower.forEach(sysPower -> {
            SysPower sysPower1 = new SysPower(sysPower.getId(), sysPower.getName(), sysPower.getType(), sysPower.getPerms(), sysPower.getUrl(), sysPower.getIsBlank(), sysPower.getPid(), sysPower.getIcon(), sysPower.getOrderNum(), sysPower.getVisible(), "0");
            rolePower.forEach(sysRolePower -> {
                if (sysRolePower.getId().equals(sysPower.getId())) {
                    sysPower1.setCheckArr("1");
                    return;
                }
            });
            sysPowerList.add(sysPower1);

        });
        return sysPowerList;
    }

    /**
     * @param userid
     * @return
     */
    @Override
    public List<TSysPermission> getall(Long userid) {
        if (userid == null) {
            TSysPermission sysPermission = new TSysPermission();
            sysPermission.setVisible(0);
            PageHelper.orderBy("order_num  is null  ASC,order_num  ASC");
            return this.selectTSysPermissionList(sysPermission);
        }
        return this.tSysPermissionMapper.findByAdminUserId(userid);
    }

    /**
     * @param tSysPermission
     * @return
     */
    @Override
    public int updateVisible(TSysPermission tSysPermission) {
        return this.baseMapper.updateById(tSysPermission);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> getSysMenus(Long userId) {
        List<SysMenu> treeList = new ArrayList<SysMenu>();
        List<TSysPermission> menuList = getall(userId);
        treeList = getSysMenus(menuList, 0L);
        return treeList;
    }

    /**
     * 递归查询权限
     *
     * @param treeList
     * @param parentId
     * @return
     */
    private List<SysMenu> getSysMenus(List<TSysPermission> treeList, Long parentId) {
        List<SysMenu> SysMenuList = new ArrayList<SysMenu>();
        if (StringUtils.isNotNull(parentId) && treeList != null && treeList.size() > 0) {
            List<SysMenu> childList = null;
            for (TSysPermission tsysPermission : treeList) {
                if (tsysPermission.getPid().equals(parentId)) {
                    if (tsysPermission.getChildCount() != null && tsysPermission.getChildCount() > 0) {
                        childList = getSysMenus(treeList, tsysPermission.getId());
                    }
                    SysMenu sysMenu = new SysMenu(tsysPermission.getId(), tsysPermission.getPid(), tsysPermission.getName(), tsysPermission.getType(), tsysPermission.getIsBlank(), tsysPermission.getIcon(), tsysPermission.getUrl(), childList);
                    SysMenuList.add(sysMenu);
                    childList = null;
                }
            }
        }

        return SysMenuList;
    }

    /**
     * @param rolid
     * @return
     */
    @Override
    public List<TSysPermission> queryRoleId(Long rolid) {
        return this.tSysPermissionMapper.queryRoleId(rolid);
    }

    /**
     * 根据权限字段查询是否存在
     *
     * @param perms
     * @return
     * @author fuce
     * @Date 2019年9月1日 上午2:06:31
     */
    public Boolean queryLikePerms(String perms) {
        QueryWrapper<TSysPermission> queryWrapper = new QueryWrapper<TSysPermission>();
        queryWrapper.like("perms" , perms);
        return this.selectTSysPermissionList(queryWrapper).size() > 0;
    }
}
