package com.site.springboot.core.config;

import com.site.springboot.core.interceptor.AdminLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SiteWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径
        registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login").excludePathPatterns("/admin/dist/**").excludePathPatterns("/admin/plugins/**");
        //addInterceptor：需要一个实现HandlerInterceptor接口的拦截器实例
        //addPathPatterns：用于设置拦截器的过滤路径规则；addPathPatterns("/**")对所有请求都拦截
        //excludePathPatterns：用于设置不需要拦截的过滤规则
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:"+Constants.FILE_UPLOAD_PATH);
    }

}
