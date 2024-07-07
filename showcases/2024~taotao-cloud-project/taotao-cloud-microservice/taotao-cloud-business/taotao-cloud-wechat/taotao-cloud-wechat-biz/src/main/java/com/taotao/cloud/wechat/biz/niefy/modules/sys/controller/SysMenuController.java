/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.wechat.biz.niefy.modules.sys.controller;

import com.github.niefy.common.annotation.SysLog;
import com.github.niefy.common.exception.RRException;
import com.github.niefy.common.utils.Constant;
import com.github.niefy.common.utils.R;
import com.github.niefy.modules.sys.entity.SysMenuEntity;
import com.github.niefy.modules.sys.service.ShiroService;
import com.github.niefy.modules.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统菜单
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/menu")
@Api(tags = {"管理后台菜单"})
public class SysMenuController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private ShiroService shiroService;

    /** 导航菜单 */
    @GetMapping("/nav")
    @ApiOperation(value = "管理后台导航菜单", notes = "不同登录用户结果不同")
    public R nav() {
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return Objects.requireNonNull(R.ok().put("menuList", menuList)).put("permissions", permissions);
    }

    /** 所有菜单列表 */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    @ApiOperation(value = "管理后台菜单列表", notes = "超级管理员可看到全部列表")
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList;
        // 超级管理员
        if (getUserId() == Constant.SUPER_ADMIN) {
            menuList = sysMenuService.list();
        } else {
            menuList = sysMenuService.queryUserAllMenu(getUserId());
        }
        for (SysMenuEntity sysMenuEntity : menuList) {
            SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }

        return menuList;
    }

    /** 选择菜单(添加、修改菜单) */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    @ApiOperation(value = "选择菜单", notes = "")
    public R select() {
        // 查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        // 添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }

    /** 菜单信息 */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    @ApiOperation(value = "菜单详情", notes = "")
    public R info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity menu = sysMenuService.getById(menuId);
        return R.ok().put("menu", menu);
    }

    /** 保存 */
    @SysLog("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    @ApiOperation(value = "保存菜单", notes = "")
    public R save(@RequestBody SysMenuEntity menu) {
        // 数据校验
        verifyForm(menu);

        sysMenuService.save(menu);

        return R.ok();
    }

    /** 修改 */
    @SysLog("修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    @ApiOperation(value = "修改菜单", notes = "")
    public R update(@RequestBody SysMenuEntity menu) {
        // 数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);

        return R.ok();
    }

    /** 删除 */
    @SysLog("删除菜单")
    @PostMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    @ApiOperation(value = "删除菜单", notes = "")
    public R delete(@PathVariable("menuId") long menuId) {
        if (menuId <= 31) {
            return R.error("系统菜单，不能删除");
        }

        // 判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return R.error("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);

        return R.ok();
    }

    /** 验证参数是否正确 */
    private void verifyForm(SysMenuEntity menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new RRException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new RRException("上级菜单不能为空");
        }

        // 菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new RRException("菜单URL不能为空");
            }
        }

        // 上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        // 目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue()
                || menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new RRException("上级菜单只能为目录类型");
            }
            return;
        }

        // 按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new RRException("上级菜单只能为菜单类型");
            }
        }
    }
}
