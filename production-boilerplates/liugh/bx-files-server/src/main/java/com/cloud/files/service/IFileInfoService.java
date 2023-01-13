package com.cloud.files.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.files.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件信息表 服务类
 * </p>
 *
 * @author liugh
 * @since 2019-06-13
 */
public interface IFileInfoService extends IService<FileInfo> {

    List<String> upload(MultipartFile[] multipartFiles, String applicationName) throws Exception;

    Page<FileInfo> findFiles(Page<FileInfo> fileInfoPage, Map<String, Object> params);

    void deleteIds(List<String> ids) throws Exception;

    String download(List<String> fileList) throws Exception;

    FileInfo insertFileInfo(String name,String applicationName, String contentType,Long size,String url,String createUser)throws Exception;

    List<FileInfo> selectFilesByIds(String[] ids);
}
