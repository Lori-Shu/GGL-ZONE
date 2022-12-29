package com.ggl.cloud.annotationaop;

import java.util.Map;
import java.util.Set;

import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@Configuration
public class MyDefinitionBeanPostProcessor implements BeanDefinitionRegistryPostProcessor,ApplicationContextAware{
    private ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO Auto-generated method stub
        this.context=applicationContext;
    }


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MyScanner myScanner = new MyScanner(registry);
        myScanner.setAnnotationClass(MyComponent.class);
        myScanner.registerFilters();
        myScanner.doScan("com.ggl.cloud.annotationaop");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // TODO Auto-generated method stub
        
    }

   

}