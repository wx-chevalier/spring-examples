package com.cloud.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.model.user.SmsVerify;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 验证码发送记录 Mapper 接口
 * </p>
 *
 * @author liugh
 * @since 2019-07-23
 */
@Mapper
public interface SmsVerifyDao extends BaseMapper<SmsVerify> {

}
