package com.ggl.cloud.annotationaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * description
 *
 * @author lori
 * @createTime 2022年9月30日-23:13:46
 */
public class MyJdkInvocationHandler implements InvocationHandler,FactoryBean<Object>{

    private final Class<?> MYPROXY;
    private Object INSTANCE=null;

    public MyJdkInvocationHandler(Class<?> clazz){
        MYPROXY=clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        // System.out.println(method.getClass().g);
        System.out.println("方法所在类的注解名："+proxy.getClass().getInterfaces()[0].getDeclaredAnnotations()[0].toString());
        // 如果代理的接口有实体类则调用“原对象”的方法（原对象需要在初始化时保存一份），如果只是一个接口方法则直接写逻辑，不会影响创建instance
        // method.invoke(myProxy, args);
        String res="默认值";
        if(args!=null&& args.length>0&&method.getName().equals("testAnnotationAop")){
             res=String.valueOf(args[0]);
             return res;
        }
        return method.invoke(MYPROXY, args);
    }

    @Override
    public synchronized Object getObject() throws Exception {
        if(INSTANCE==null&&MYPROXY.isInterface()){
            INSTANCE=Proxy.newProxyInstance(MYPROXY.getClassLoader(), new Class[]{MYPROXY}, this);
        }
        return INSTANCE;
    }

    @Override
    public Class<?> getObjectType() {
        return MYPROXY;
    }
    
}
