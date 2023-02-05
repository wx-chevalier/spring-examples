package com.cloud.user.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.utils.ComUtil;
import com.cloud.utils.ResultHelper;
import com.cloud.model.user.Organization;
import com.cloud.user.service.IOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 机构表 前端控制器
 * </p>
 *
 * @author liugh123
 * @since 2019-07-04
 */
@RestController
@RequestMapping("/organization")
@Api(tags = "机构模块")
public class OrganizationController {

    @Autowired
    private IOrganizationService organizationService;

    /**
     * 机构查询
     */
    @ApiOperation(value = "机构分页查询")
    @GetMapping("/pageList")
    public ResultHelper findUsers(
            @RequestParam(name = "current", defaultValue = "1", required = false) Integer current,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "organizationName", defaultValue = "", required = false) String organizationName) {
        Page<Organization> organizationPage = new Page<>(current, size);
        Wrapper<Organization> wrapper = new EntityWrapper<>();
        if(!ComUtil.isEmpty(organizationName)){
            wrapper.like("organization_name",organizationName);
        }
        return ResultHelper.succeed(organizationService.selectPage(organizationPage,wrapper));
    }

    //提供给map-service调用
    @ApiOperation(value = "根据机构名字模糊查询机构信息")
    @GetMapping("/findOrganizationByName")
    public List<Organization> findOrganizationById(@RequestParam("organizationName") String organizationName) {
        Wrapper<Organization> wrapper = new EntityWrapper<>();
        if (!ComUtil.isEmpty(organizationName)) {
            wrapper.like("organization_name", organizationName);
        }
       return organizationService.selectList(wrapper);
    }

    @GetMapping("/findOrganizationById")
    public Organization findOrganizationById(@RequestParam("id") Integer id){
        Organization organization = organizationService.selectById(id);
        return organization;
    }

}

