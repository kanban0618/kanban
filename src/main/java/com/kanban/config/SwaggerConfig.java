package com.kanban.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2 //3.0之前
@EnableOpenApi//3.0之后
public class SwaggerConfig {

}
