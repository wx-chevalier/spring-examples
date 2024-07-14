
package com.fc.v2.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.v2.model.auto.TSysPermission;
import com.fc.v2.model.custom.SysMenu;
import com.fc.v2.model.custom.SysPower;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * 权限Service接口
 *
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysPermissionService extends IService<TSysPermission> {
    /**
     * 查询权限
     *
     * @param id 权限ID
     * @return 权限
     */
    public TSysPermission selectTSysPermissionById(Long id);

    /**
     * 查询权限
     *
     * @param ids 权限ID
     * @return 权限
     */
    public List<TSysPermission> selectTSysPermissionByIds(List<Long> ids);

    /**
     * 查询权限列表
     *
     * @param queryWrapper 权限
     * @return 权限集合
     */
    public List<TSysPermission> selectTSysPermissionList(Wrapper<TSysPermission> queryWrapper);

    /**
     * 查询权限列表
     *
     * @param tSysPermission 权限
     * @return 权限集合
     */
    public List<TSysPermission> selectTSysPermissionList(TSysPermission tSysPermission);

    /**
     * 新增权限
     *
     * @param tSysPermission 权限
     * @return 结果
     */
    public int insertTSysPermission(TSysPermission tSysPermission);

    /**
     * 修改权限
     *
     * @param tSysPermission 权限
     * @return 结果
     */
    public int updateTSysPermission(TSysPermission tSysPermission);

    /**
     * 批量删除权限
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public ImmutablePair<Integer, String> deleteTSysPermissionByIds(String ids);

    /**
     * 删除权限信息
     *
     * @param id 权限ID
     * @return 结果
     */
    public int deleteTSysPermissionById(Long id);

    /**
     * 检查权限name
     *
     * @param tsysPermission
     * @return
     */
    public int checkNameUnique(TSysPermission tsysPermission);

    /**
     * 检查权限URL
     *
     * @param tsysPermission
     * @return
     */
    public int checkURLUnique(TSysPermission tsysPermission);

    /**
     * 检查权限perms字段
     *
     * @param tsysPermission
     * @return
     */
    public int checkPermsUnique(TSysPermission tsysPermission);

    /**
     * @param roleId
     * @return
     */
    public List<SysPower> getRolePower(Long roleId);

    /**
     * @param userid
     * @return
     */
    public List<TSysPermission> getall(Long userid);

    /**
     * 修改权限状态展示或者不展示
     *
     * @param tSysPermission
     * @return
     */
    public int updateVisible(TSysPermission tSysPermission);

    /**
     * 根据用户id查询菜单栏
     *
     * @param userId
     * @return
     */
    public List<SysMenu> getSysMenus(Long userId);

    /**
     * @param rolid
     * @return
     */
    public List<TSysPermission> queryRoleId(Long rolid);
}
