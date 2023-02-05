package com.fc.v2.common.base;

import cn.hutool.core.util.StrUtil;
import com.fc.v2.common.conf.V2Config;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResuTree;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.service.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * web层通用数据处理
 *
 * @author fuce
 * @ClassName: BaseController
 * @date 2018年8月18日
 */
@Controller
public class BaseController {

    /**
     * 系统用户
     */
    @Autowired
    public ITSysUserService sysUserService;

    /**
     * 系统角色
     */
    @Autowired
    public ITSysRoleService sysRoleService;

    /**
     * 权限
     */
    @Autowired
    public ITSysPermissionService sysPermissionService;

    /**
     * 日志操作
     */
    @Autowired
    public ITSysOperLogService sysOperLogService;

    /**
     * 公告
     */
    @Autowired
    public ITSysNoticeService sysNoticeService;

    /**
     * 文件上传
     */
    @Autowired
    public ITSysFileService sysFileService;

    /**
     * 配置文件
     */
    @Autowired
    public V2Config v2Config;

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 返回成功
     *
     * @return
     */
    public AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     *
     * @return
     */
    public AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     *
     * @param message
     * @return
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回object数据
     *
     * @param code
     * @param data
     * @return
     */
    public AjaxResult success(int code, String message, Object data) {
        return AjaxResult.success(code, message, data);
    }

    /**
     * 返回失败消息
     *
     * @param message
     * @return
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     *
     * @param code
     * @param message
     * @return
     */
    public AjaxResult error(int code, String message) {
        return AjaxResult.error(code, message);
    }

    /**
     * 返回object数据
     *
     * @param code
     * @param data
     * @return
     */
    public AjaxResult retobject(int code, Object data) {
        return AjaxResult.successData(code, data);
    }

    /**
     * 页面跳转
     *
     * @param url
     * @return
     */
    public String redirect(String url) {
        return StrUtil.format("redirect:{}", url);
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        Tablepar tablepar = Tablepar.buildPageRequest();

        int pageNum = tablepar.getPage();
        int pageSize = tablepar.getLimit();
        if (pageNum != 0 && pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize, tablepar.getOrderByColumn());
        }
    }

    /**
     * 返回 Tree 数据
     *
     * @param data
     * @return
     */
    protected static ResuTree dataTree(Object data) {
        ResuTree resuTree = new ResuTree();
        resuTree.setData(data);
        return resuTree;
    }

    /**
     * 返回数据表格数据
     *
     * @param data  表格分页数据
     * @param count
     * @return
     */
    protected static ResultTable pageTable(Object data, long count) {
        return ResultTable.pageTable(count, data);
    }

    /**
     * 返回数据表格数据
     *
     * @param data 表格分页数据
     * @return
     */
    protected static ResultTable dataTable(Object data) {
        return ResultTable.dataTable(data);
    }

    /**
     * 返回树状表格数据
     *
     * @param data 表格分页数据
     * @return
     */
    protected static ResultTable treeTable(Object data) {
        return ResultTable.dataTable(data);
    }

}
