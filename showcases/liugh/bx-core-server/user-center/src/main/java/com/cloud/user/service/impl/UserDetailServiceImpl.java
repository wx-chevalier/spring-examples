package com.cloud.user.service.impl;

import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.user.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 根据用户名获取用户<br>
 * <p>
 * 密码校验请看下面两个类
 *
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserService userClient;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 为了支持多类型登录，这里username后面拼装上登录类型,如username|type
        String[] params = username.split("\\|");
        username = params[0];// 真正的用户名或手机号

        LoginAppUser loginAppUser = null;
        if (params.length == 1 || (CredentialType.USERNAME == CredentialType.valueOf(params[1]))) {
            loginAppUser = userClient.findByUsername(username);
            if (loginAppUser == null) {
                throw new AuthenticationCredentialsNotFoundException("用户不存在");
            } else if (!loginAppUser.isEnabled()) {
                throw new DisabledException("用户已作废");
            }
        } else {
            // 登录类型
            CredentialType credentialType = CredentialType.valueOf(params[1]);
            if (CredentialType.PHONE == credentialType) {// 短信登录
                loginAppUser = userClient.findByPhone(username);
                loginAppUser.setPassword(passwordEncoder.encode(username));
            } else if (CredentialType.THIRD == credentialType) {// 第三方登陆
                loginAppUser = userClient.findByUsername(username);
                loginAppUser.setPassword(passwordEncoder.encode(username));
            }
        }

        return loginAppUser;
    }

}
