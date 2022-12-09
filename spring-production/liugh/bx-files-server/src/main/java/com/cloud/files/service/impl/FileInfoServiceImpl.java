package com.cloud.files.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.constants.Constant;
import com.cloud.files.entity.FileInfo;
import com.cloud.files.mapper.FileInfoMapper;
import com.cloud.files.service.IFileInfoService;
import com.cloud.utils.*;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * <p>
 * 文件信息表 服务实现类
 * </p>
 *
 * @author liugh
 * @since 2019-06-13
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public List<String> upload(MultipartFile[] multipartFiles, String applicationName) throws Exception {
        List<String> filePaths = new ArrayList<>();
        if(!ComUtil.isEmpty(multipartFiles) && multipartFiles.length != 0) {
            for (MultipartFile multipartFile : multipartFiles) {

                String originalFilename = multipartFile.getOriginalFilename();
                String fileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.indexOf("."));
                String fileUrl = FileUtil.saveFile(multipartFile.getInputStream(), "/" + applicationName + "/" + fileName);

                String thumbFileName = "/thumbFile/" + fileName;
                String contentType = FileUtil.getFileType(originalFilename);
                String thumbFileUrl = "";
                if (Constant.FileType.FILE_IMG.equals(contentType)) {
                    thumbFileUrl = fileUrl;
                    //大于1m时启动图片压缩
                    if(multipartFile.getSize() >= FileUtil.FILE_SIZE){
                        FileUtil.saveThumbFile(fileUrl, thumbFileName);
                        thumbFileUrl = thumbFileName;
                    }
                }

                FileInfo fileInfo = FileInfo.builder()
                        .id(UUID.randomUUID().toString().replaceAll("-",""))
                        .name(multipartFile.getOriginalFilename())
                        .applicationName(applicationName)
                        .contentType(contentType)
                        .size(multipartFile.getSize())
                        .thumbUrl(thumbFileUrl)
                        .url(fileUrl)
                        .createUser(AppUserUtil.getLoginAppUser().getId())
                        .createTime(System.currentTimeMillis()).build();
                this.insert(fileInfo);
            }
        }
        return filePaths;
    }

    @Override
    public Page<FileInfo> findFiles(Page<FileInfo> fileInfoPage, Map<String, Object> params) {
        List<FileInfo> fileList = fileInfoMapper.findFiles(fileInfoPage, params);
        return fileInfoPage.setRecords(fileList);
    }

    @Override
    public void deleteIds(List<String> ids) throws Exception {

        List<FileInfo> files = this.selectBatchIds(ids);
        List<String> filesUrl = new ArrayList<>();
        if (!ComUtil.isEmpty(files)) {
            for(FileInfo file : files) {
                filesUrl.add(file.getUrl());
                filesUrl.add(file.getThumbUrl());
            }
        }
        this.deleteBatchIds(ids);
        FileUtil.deleteFiles(filesUrl);
    }

    @Override
    public String download(List<String> urlList) throws Exception {
        if (ComUtil.isEmpty(urlList)) {
            return null;
        }
        String fileDir = FileUtil.fileUrl + "/zip/";
        FileUtil.createDir(fileDir);
        if (urlList.size() > 1) {//批量下载
            fileDir += DateTimeUtil.formatDateTimetoString(new Date(), "yyyyMMddHHmmss") + "/";
            FileUtil.createDir(fileDir);
            for (String url : urlList) {
                FileUtil.copy(new File(FileUtil.fileUrl+url), new File(fileDir +url.substring(url.lastIndexOf('/'))));
            }

            String fileZip = "文件信息" +DateTimeUtil.formatDateTimetoString(new Date(), "yyyyMMddHHmmss") + ".zip";
            FileUtil.fileToZip(fileDir, FileUtil.fileUrl + "/zip/", fileZip);
            FileUtil.deleteDirectory(fileDir);
            return "/zip/" + fileZip;
        } else {
            return urlList.get(0);
        }
    }

    @Transactional
    @LcnTransaction
    @Override
    public FileInfo insertFileInfo(String name,String applicationName, String contentType,Long size,String url,String createUser) throws Exception {
        FileInfo fileInfo = FileInfo.builder().applicationName(applicationName).name(name)
                .contentType(contentType).createUser(createUser).url(url).
                        createTime(System.currentTimeMillis()).size(size).id(GenerationSequenceUtil.generateUUID()).build();
        this.insert(fileInfo);
        return fileInfo;
    }

    @Override
    public List<FileInfo> selectFilesByIds(String[] ids) {
        List<FileInfo> list= fileInfoMapper.selectFilesByIds(ids);
        return list;
    }
}
