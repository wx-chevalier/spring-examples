package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.model.user.Organization;
import com.cloud.user.dao.OrganizationDao;
import com.cloud.user.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author liugh123
 * @since 2019-07-04
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationDao, Organization> implements IOrganizationService {

    @Autowired
    private OrganizationDao organizationDao;


}
