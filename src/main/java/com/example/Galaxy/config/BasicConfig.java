package com.example.Galaxy.config;

import com.example.Galaxy.exception.OverallExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域请求
 * @author zhh
 * @time 2020-6-21
 */
@Configuration
public class BasicConfig implements WebMvcConfigurer {
    static final String ORIGINS[] = new String[]{"GET", "POST", "PUT", "DELETE"};

    /**
     * 设置跨域请求
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(ORIGINS)
                .maxAge(3600)
                .allowedHeaders("*");
    }

//    @Bean(name = "exceptionResolver")
//    public OverallExceptionResolver exceptionResolver() {
//        OverallExceptionResolver resolver = new OverallExceptionResolver();
//        return resolver;
//    }
}
