package com.cloud.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.ThirdPartyUser;
import com.cloud.model.user.UserThirdparty;


/**
 * <p>
 * 第三方用户表 服务类
 * </p>
 *
 * @author liugh
 */
public interface IUserThirdpartyService extends IService<UserThirdparty> {

    AppUser insertThirdPartyUser(ThirdPartyUser param)throws Exception;
}
