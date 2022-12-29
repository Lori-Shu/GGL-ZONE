package com.ggl.cloud.annotationaop;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * description
 *
 * @author lori
 * @createTime 2022年10月03日-15:38:25
 */
public class MyScanner extends ClassPathBeanDefinitionScanner{

    private Class<? extends Annotation> annotationClass;

    public MyScanner(BeanDefinitionRegistry registry) {
        super(registry);
        
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) { 
   
        this.annotationClass = annotationClass;
    }

    public void registerFilters(){ 
   
        if(!this.annotationClass.isInstance(Annotation.class)){ 
   
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
        }


    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) { 
   
            logger.info("No MyScanAnnotation class was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
            throw new RuntimeException("包扫描创建bean异常！");
        }else{
            processBeanDefinitions(beanDefinitions);
            return beanDefinitions;
        }

        
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {

    GenericBeanDefinition definition;
    for (BeanDefinitionHolder holder : beanDefinitions) { 
        definition = (GenericBeanDefinition) holder.getBeanDefinition();
        definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName()); // issue #59
        definition.setBeanClass(MyJdkInvocationHandler.class);
    }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // 这里设置是否包含接口类型，如果包含需要处理实例化或代理后才能在doscan方法返回
        return true;
    }  
}
