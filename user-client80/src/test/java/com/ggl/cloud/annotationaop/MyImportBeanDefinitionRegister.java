package com.ggl.cloud.annotationaop;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author lori
 * @createTime 2022年9月30日-22:41:13
 */
// @Configuration
public class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar{

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("进入初始化自定义包扫描方法");
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
        // 注册自定义扫描的注解
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyComponent.class));

        // 获取包扫描的路径
        // Map<String, Object> annMap =
        // importingClassMetadata.getAnnotationAttributes(ComponentScan.class.getName());
        // AnnotationAttributes annotationAttributes =
        // AnnotationAttributes.fromMap(annMap);
        // assert annotationAttributes != null;
        // String[] basePackages = annotationAttributes.getStringArray("basePackages");
        // System.out.println(basePackages);

        // 默认取主配置类下的包路径，springboot使用的就是这种方式
        // AutoConfigurationPackages.get(this.beanFactory);
        // ClassUtils.getPackageName("mainClass");

        // 扫描的包路径
        scanner.scan("com.ggl.cloud.annotationaop");
    }

 

}
