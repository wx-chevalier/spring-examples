package com.cloud.files.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.utils.ComUtil;
import com.cloud.utils.ResultHelper;
import com.cloud.utils.StringUtil;
import com.cloud.files.entity.FileInfo;
import com.cloud.files.service.IFileInfoService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 文件服务
 * @author liugh
 */
@Api(tags = "文件模块")
@RestController
@RequestMapping("/files")

public class FilesController  {
    @Autowired
    private IFileInfoService fileInfoService;


    @ApiOperation(value = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "文件数组", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicationName", value = "服务名称", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping
    public ResultHelper upload(@RequestParam("files") MultipartFile[] files,
                               @RequestParam("applicationName") String applicationName) throws Exception {

        List<String> filePaths = fileInfoService.upload(files, applicationName);
        return ResultHelper.succeed(filePaths);
    }

    @ApiOperation(value = "文件表插入数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applicationName", value = "服务名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文件名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contentType", value = "文件类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "文件大小", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "url", value = "地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/info")
    public ResultHelper insertFileInfo(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "applicationName") String applicationName,
            @RequestParam(name = "contentType") String contentType,
            @RequestParam(name = "size") Long size,
            @RequestParam(name = "url") String url,
            @RequestParam(name = "createUser") String createUser) throws Exception {
        FileInfo fileInfo =fileInfoService.insertFileInfo(name,applicationName,contentType,size,url,createUser);
        return ResultHelper.succeed(fileInfo);
    }

    @ApiOperation(value = "根据id获取单条文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文件主键", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping("/infoById")
    public ResultHelper insertFileInfo(@RequestParam("id") String id) throws Exception {
        return ResultHelper.succeed(fileInfoService.selectById(id));
    }


    @ApiOperation(value = "文件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页的数量", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contentType", value = "文件类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicationName", value = "服务名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "ASC/DESC", dataType = "String", paramType = "query")
    })
    @GetMapping
    public ResultHelper findFiles(
            @RequestParam(name = "current", defaultValue = "1", required = false) Integer current,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) Map<String, Object> params) {
        if (params.containsKey("orderBy")) {
            params.put("orderBy", StringUtil.HumpToUnderline(params.get("orderBy").toString()));
        }

        return ResultHelper.succeed(fileInfoService.findFiles(new Page<FileInfo>(current, size), params));
    }

    @ApiOperation(value = "文件删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "文件主键,逗号隔开", required = true, dataType = "String", paramType = "query")
    })
    @DeleteMapping
    public ResultHelper deleteFiles(@RequestParam(name = "ids") String ids) throws Exception {

        List<String> idList;
        if (ids.contains(",")) {
            idList = Arrays.asList(ids.split(","));
        } else {
            idList = new ArrayList<>();
            idList.add(ids);
        }
        fileInfoService.deleteIds(idList);
        return ResultHelper.succeed(null);
    }

    @ApiOperation(value = "下载/批量下载", notes = "需要header里加入Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contentType", value = "文件类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicationName", value = "服务名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "urls", value = "文件url逗号隔开", dataType = "String", paramType = "query")
    })
    @GetMapping("/download")
    public ResultHelper download(@RequestParam Map<String, Object> params) throws Exception {

        List<String> urlList;
        if (!ComUtil.isEmpty(params.get("urls"))) {
            String urls = params.get("urls").toString();
            if (urls.contains(",")) {
                urlList = Arrays.asList(urls.split(","));
            } else {
                return ResultHelper.succeed(urls);
            }

        } else {
            urlList = new ArrayList<>();
            Lists.newArrayList();
            List<FileInfo> fileList = fileInfoService.findFiles(new Page<FileInfo>(1, Integer.MAX_VALUE), params).getRecords();
            fileList.forEach(file -> urlList.add(file.getUrl()));
        }

        return ResultHelper.succeed(fileInfoService.download(urlList));
    }




    @ApiOperation(value = "根据文件id集合，查找文件对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "文件id集合字符串", dataTypeClass = Array.class, paramType = "body")
    })
    @PostMapping("/selectFilesByIds")
    public List<FileInfo> selectFilesByIds(  @RequestBody String[] ids){
        List<FileInfo> list= fileInfoService.selectFilesByIds(ids);
        return list;
    }



}
