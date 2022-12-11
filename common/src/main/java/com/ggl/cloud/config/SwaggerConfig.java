// /*
// *
// *@Date:2022年5月01日
// *
// *@Author:Lori Shu
// *
// */
// package com.ggl.cloud.config;




// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;



// /**
//  * 
//  * description
//  *
//  * @author Lori
//  * createTime 2022年8月19日-下午2:24:30
//  *
//  */

// @Configuration

// public class SwaggerConfig {
//     @Bean
//     public Docket webApiConfig(){
//         return new Docket(DocumentationType.SWAGGER_2)
//         .groupName("webApi")
//         .apiInfo(webApiInfo())
//         .select()
//         // .apis(RequestHandlerSelectors.basePackage("com.controller"))
//         // .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//         .paths(PathSelectors.any())
//         .build();
//     }
//     private ApiInfo webApiInfo(){
//         return new ApiInfoBuilder()
//         .title("GGL-ZONE服务端接口文档")
//         .description("接口文档")
//         .version("1.0")
//         .contact(new Contact("Lori Shu","GGL-ZONE","12345@dd.com"))
//         .build();
//     }
    
// }
