package com.fc.v2.shiro.service;

import com.fc.v2.model.auto.TSysPermission;
import com.fc.v2.model.auto.TSysRole;
import com.fc.v2.model.auto.TSysUser;
import com.fc.v2.service.ITSysPermissionService;
import com.fc.v2.service.ITSysRoleService;
import com.fc.v2.service.ITSysUserService;
import com.fc.v2.util.StringUtils;
import cn.hutool.core.collection.CollUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 身份校验核心类
 *
 * @author fuce
 * @className: MyShiroRealm
 * @date 2018年8月25日
 */
@Service
public class MyShiroRealm extends AuthorizingRealm {

    /**
     * 用户
     */
    @Autowired
    private ITSysUserService userService;

    /**
     * 权限
     */
    @Autowired
    private ITSysPermissionService permissionService;

    /**
     * 角色
     */
    @Autowired
    private ITSysRoleService roleService;


    /**
     * 认证登陆
     * @param token
     * @return authenticationInfo
     * @throws
     */
    @SuppressWarnings("unused")
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求        
        if (StringUtils.isNull(token.getPrincipal())) {
            return null;
        }

        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        // 通过username从数据库中查找 User对象，如果找到，没找到.
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        TSysUser userInfo = new TSysUser();
        userInfo.setUsername(username);
        List<TSysUser> list = userService.selectTSysUserList(userInfo);

        if (CollUtil.isEmpty(list)) {
            return null;
        }

        userInfo = list.get(0);
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), getName());
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        if (StringUtils.isNull(principals)) {
            throw new AuthorizationException("principals should not be null");
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        TSysUser userinfo = (TSysUser) principals.getPrimaryPrincipal();
        Long uid = userinfo.getId();
        List<TSysRole> tsysRoles = roleService.queryUserRole(uid);
        tsysRoles.forEach(userrole -> {
            //添加角色名字
            authorizationInfo.addRole(userrole.getName());
            List<TSysPermission> permissions = permissionService.queryRoleId(userrole.getId());
            permissions.forEach(permission ->{
                //角色下面的权限
                if (StringUtils.isNotEmpty(permission.getPerms())){
                    authorizationInfo.addStringPermission(permission.getPerms());
                }
            });
        });

        return authorizationInfo;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
