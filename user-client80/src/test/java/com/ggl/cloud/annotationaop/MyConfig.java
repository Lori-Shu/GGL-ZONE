package com.ggl.cloud.annotationaop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * description
 *
 * @author lori
 * @createTime 2022年9月30日-22:50:26
 */

@Configuration
@Import({MyDefinitionBeanPostProcessor.class})
public class MyConfig {
    // @Bean
    // public MyInterface myInterface(){
    //     AnnotationUtils.;
    //     MyJdkInvocationHandler myJdkInvocationHandler = new MyJdkInvocationHandler();
        
    //     return (MyInterface)myJdkInvocationHandler.getInstance();
    // }


}
