package com.ggl.cloud.annotationaop;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * description
 *
 * @author lori
 * @createTime 2022年9月30日-22:47:42
 */
public class TestAnnotation {
    @Test
    public void testAnnotation(){
    ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfig.class);
    // System.out.println(ctx.getBeanDefinitionCount());
//     for(String s:ctx.getBeanDefinitionNames()){
//     System.out.println(s);
// }
Object bean = ctx.getBean("myInterface");
System.out.println(bean);
String testAnnotationAop = ((MyInterface)bean).testAnnotationAop("helloScanner!");
System.out.println(testAnnotationAop);
    // System.out.println(MyInterface.class.getDeclaredAnnotations()[0].toString());
    //TODO:给Student类上添加 @MyComponent2  注解
    // MyInterface bean = ctx.getBean(MyInterface.class);
    // if(bean==null){
    // System.out.println("bean为空");
    // }
    // 连toString都被代理了
    // System.out.println("bean = " + bean.toString());
    // System.out.println(bean.testAnnotationAop("hello!world!"));
    }
}
