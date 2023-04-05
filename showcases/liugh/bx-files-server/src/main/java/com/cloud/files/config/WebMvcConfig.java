package com.cloud.files.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by dream
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有D:/files/myTest/ 访问都映射到/myTest/** 路径下
        registry.addResourceHandler("/test/**").addResourceLocations("file:/data/gfc-project/file/test/");
        registry.addResourceHandler("/thumbFile/**").addResourceLocations("file:/data/gfc-project/file/thumbFile/");
        registry.addResourceHandler("/zip/**").addResourceLocations("file:/data/gfc-project/file/zip/");
        registry.addResourceHandler("/sld/**").addResourceLocations("file:/data/gfc-project/file/sld/");
        super.addResourceHandlers(registry);
    }
}