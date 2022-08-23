package com.kanban.config;

import com.kanban.tool.PathTool;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

//        registry.addResourceHandler("/uploadFile/**").addResourceLocations("file:D:/uploadFile/");
        registry.addResourceHandler("/uploadFile/**").addResourceLocations("file:"+ PathTool.getCurrentJarPath() +"/");
    }
}
