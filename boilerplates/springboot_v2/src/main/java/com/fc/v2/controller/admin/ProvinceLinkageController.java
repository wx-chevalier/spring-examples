package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.model.auto.*;
import com.fc.v2.service.ITSysAreaService;
import com.fc.v2.service.ITSysCityService;
import com.fc.v2.service.ITSysProvinceService;
import com.fc.v2.service.ITSysStreetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 省份联动controller
 *
 * @author fuce
 * @ClassName: ProvinceLinkageController
 * @date 2019-10-05 11:19
 */
@Api(value = "省份联动controller")
@Controller
@RequestMapping("/ProvinceLinkageController")
public class ProvinceLinkageController extends BaseController {

    @Autowired
    private ITSysProvinceService sysProvinceService;

    @Autowired
    private ITSysCityService sysCityService;

    @Autowired
    private ITSysAreaService sysAreaService;

    @Autowired
    private ITSysStreetService sysStreetService;

    private String prefix = "admin/sysProvince";

    /**
     * 分页list页面
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    public String view(ModelMap model) {
        model.addAttribute("provinceList", sysProvinceService.selectTSysProvinceList(new QueryWrapper<TSysProvince>()));
        return prefix + "/list";
    }

    /**
     * 市查询
     *
     * @param pid
     * @return
     */
    @ApiOperation(value = "市查询", notes = "市查询")
    @GetMapping("/getCity")
    @ResponseBody
    public List<TSysCity> getCity(String pid) {
        QueryWrapper<TSysCity> queryWrapper = new QueryWrapper<TSysCity>();
        queryWrapper.eq("province_code", pid);
        return sysCityService.selectTSysCityList(queryWrapper);

    }

    /**
     * 区查询
     *
     * @param pid
     * @return
     */
    @ApiOperation(value = "区查询", notes = "区查询")
    @GetMapping("/getArea")
    @ResponseBody
    public List<TSysArea> getArea(String pid) {
        QueryWrapper<TSysArea> queryWrapper = new QueryWrapper<TSysArea>();
        queryWrapper.eq("city_code", pid);
        return sysAreaService.selectTSysAreaList(queryWrapper);

    }

    /**
     * 街道查询
     *
     * @param pid
     * @return
     */
    @ApiOperation(value = "街道查询", notes = "街道查询")
    @GetMapping("/getStreet")
    @ResponseBody
    public List<TSysStreet> getStreet(String pid) {
        QueryWrapper<TSysStreet> queryWrapper = new QueryWrapper<TSysStreet>();
        queryWrapper.eq("area_code", pid);
        return sysStreetService.selectTSysStreetList(queryWrapper);

    }
}
