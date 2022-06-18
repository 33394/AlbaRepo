package com.example.demo.confige;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

  //用于放行跨域请求
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
            .allowCredentials(true)
            .allowedOriginPatterns("*")
            .allowedHeaders(CorsConfiguration.ALL)
            .allowedMethods(CorsConfiguration.ALL)
            .maxAge(3600);
  }
  //用于映射静态资源路径
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String path = "D:/CodeUtil/高级java课程设计/chat_system/src/main/resources/static/files/";
    registry.addResourceHandler("/files/**").addResourceLocations("file:" + path);
  }


}
