package com.example.Galaxy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  跨域请求
 * @author zhh
 * @time 2020-6-21
 */
@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {
    static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" };

    @Override
    public  void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(ORIGINS)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
