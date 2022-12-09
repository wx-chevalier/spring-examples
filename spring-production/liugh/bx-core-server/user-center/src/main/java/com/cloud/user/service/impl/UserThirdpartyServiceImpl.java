package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.constants.Constant;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.ThirdPartyUser;
import com.cloud.model.user.UserThirdparty;
import com.cloud.model.user.constants.RoleType;
import com.cloud.user.dao.UserThirdpartyDao;
import com.cloud.user.service.AppUserService;
import com.cloud.user.service.IUserThirdpartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方用户表 服务实现类
 * </p>
 *
 * @author liugh
 */
@Service
public class UserThirdpartyServiceImpl extends ServiceImpl<UserThirdpartyDao, UserThirdparty> implements IUserThirdpartyService {

    @Autowired
    private AppUserService appUserService;

    @Override
    public AppUser insertThirdPartyUser(ThirdPartyUser param) throws Exception {

        AppUser appUser = new AppUser();
        appUser.setPassword(Constant.PASSWORD);
        appUser.setRePassword(Constant.PASSWORD);
        appUser.setUsername("gfc" + param.getOpenid());
        appUser.setRole(RoleType.USER.name());
        appUser.setType("4");
        appUser = appUserService.addAppUser(appUser);

        // 初始化第三方信息
        UserThirdparty thirdparty = new UserThirdparty();
        thirdparty.setProviderType(param.getProvider());
        thirdparty.setOpenId(param.getOpenid());
        thirdparty.setCreateTime(System.currentTimeMillis());
        thirdparty.setUserNo(appUser.getId());
        thirdparty.setStatus(1);
        this.insert(thirdparty);
        return appUser;
    }
}
