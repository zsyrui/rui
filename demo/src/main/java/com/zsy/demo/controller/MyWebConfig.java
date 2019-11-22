package com.zsy.demo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
     public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/dome/**")
                .allowedHeaders("authorization","content-type")
                .maxAge(1800);
    }
}
