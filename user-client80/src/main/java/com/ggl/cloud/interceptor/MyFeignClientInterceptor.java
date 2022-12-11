package com.ggl.cloud.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * Feign拦截器
 */
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:54:17
 *
 */
@Component
public class MyFeignClientInterceptor implements RequestInterceptor {
    private final String tokenField = "token";
    @Override
    public void apply(RequestTemplate template) {
        try {
            // 获取对象
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (requestAttributes != null) {
                // 获取请求对象
                HttpServletRequest request = requestAttributes.getRequest();
                // 获取当前请求的header，获取到jwt令牌
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
                        String headerValue = request.getHeader(headerName);
                        // 将header向下传递
                        if (tokenField.equals(headerName)) {
                            template.header(headerName, headerValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}