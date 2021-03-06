package com.healthclubs.pengke.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {


    /** 文件地址 */
    @Value("${upload.file-space}")
    private String filePath;


   /* public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/wechatLogin/coachWeixiLogin", "/wechatLogin/login_url")
                .excludePathPatterns("/v2/api-docs/**")
                .excludePathPatterns("/v2/api-docs-ext/**")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/doc.html");;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //super.addResourceHandlers(registry);
    }


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }*/


    @Bean
    public RequestInterceptor getSessionInterceptor()
    {
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//开通验证注销掉upi 打开api
        registry.addInterceptor(getSessionInterceptor())
                .addPathPatterns("/upi/**")
                //.addPathPatterns("/api/**")
                .excludePathPatterns("/wechatLogin/coachWeixiLogin")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:"+filePath+"/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}