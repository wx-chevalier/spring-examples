package com.taotao.cloud.sys.biz.milliondataexport.mybatis;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("download")
public class HelloController {
    private final AuthorsService authorsService;

    public HelloController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping("streamDownload")
    public void streamDownload(HttpServletResponse response)
            throws IOException {
        authorsService.streamDownload(response);
    }

    @GetMapping("traditionDownload")
    public void traditionDownload(HttpServletResponse response)
            throws IOException {
        authorsService.traditionDownload (response);
    }
}  
