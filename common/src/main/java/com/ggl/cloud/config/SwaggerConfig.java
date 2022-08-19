/*
*
*@Date:2022年5月01日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket WebApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
        .groupName("webApi")
        .apiInfo(webApiInfo())
        .select()
        // .apis(RequestHandlerSelectors.basePackage("com.controller"))
        // .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
        .paths(PathSelectors.any())
        .build();
    }
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
        .title("GGL-ZONE服务端接口文档")
        .description("接口文档")
        .version("1.0")
        .contact(new Contact("Lori Shu","GGL-ZONE","12345@dd.com"))
        .build();
    }
    
}
