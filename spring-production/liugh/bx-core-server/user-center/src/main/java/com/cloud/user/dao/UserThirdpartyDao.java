package com.cloud.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.model.user.UserThirdparty;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 第三方用户表 Mapper 接口
 * </p>
 *
 * @author liugh
 * @since 2018-07-24
 */
@Mapper
public interface UserThirdpartyDao extends BaseMapper<UserThirdparty> {

}
