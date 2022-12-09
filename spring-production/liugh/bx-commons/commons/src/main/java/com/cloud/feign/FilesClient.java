package com.cloud.feign;

import com.cloud.config.FeignInterceptorConfig;
import com.cloud.utils.ResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/8.
 */


@FeignClient(value = "files-server",configuration = FeignInterceptorConfig.class,url = "http://127.0.0.1:8090/files")
public interface FilesClient {


    @PostMapping("/info")
    ResultHelper insertFileInfo(@RequestParam(name = "name") String name,
                                @RequestParam(name = "applicationName") String applicationName,
                                @RequestParam(name = "contentType") String contentType,
                                @RequestParam(name = "size") Long size,
                                @RequestParam(name = "url") String url,
                                @RequestParam(name = "createUser") String createUser) throws Exception ;

    @PostMapping("/selectFilesByIds")
    List<Map> selectFilesByIds(@RequestBody String[] ids);
}
