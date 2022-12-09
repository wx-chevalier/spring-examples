package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.model.user.SmsVerify;
import com.cloud.user.dao.SmsVerifyDao;
import com.cloud.user.service.ISmsVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 验证码发送记录 服务实现类
 * </p>
 *
 * @author liugh123
 */
@Slf4j
@Service
public class SmsVerifyServiceImpl extends ServiceImpl<SmsVerifyDao, SmsVerify> implements ISmsVerifyService {

    @Override
    public List<SmsVerify> getByMobileAndCaptchaAndType(String mobile, String captcha, Integer type) {

        EntityWrapper ew = new EntityWrapper();
        ew.eq("mobile", mobile);
        ew.eq("sms_verify", captcha);
        ew.eq("sms_type", type);
        return this.selectList(ew);
    }
}
