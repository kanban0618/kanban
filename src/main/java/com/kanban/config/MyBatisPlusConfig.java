package com.kanban.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @time 2022/8/10 10:26
 * @Author SmallWatermelon
 * @since 1.8
 */

@Configuration
@MapperScan("com.kanban.mapper")
public class MyBatisPlusConfig {

    /**
     * mybatis-Plus分页插件
     */
    @Bean
    public PaginationInterceptor PaginationInterceptor() {
        return new PaginationInterceptor();
    }
}
