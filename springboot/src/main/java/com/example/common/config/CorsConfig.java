package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域请求配置类
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 创建一个基于URL的跨域配置源对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 创建一个跨域配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 设置允许的来源（Origin），这里建议在生产环境中指定具体的前端域名以增强安全性
        corsConfiguration.addAllowedOriginPattern("*");

        // 设置允许的请求头（Header），这里设置为允许所有头信息通过
        corsConfiguration.addAllowedHeader("*");

        // 设置允许的请求方法，允许所有方法
        corsConfiguration.addAllowedMethod("*");

        // 设置是否允许携带Cookie等凭证信息
        corsConfiguration.setAllowCredentials(true);

        // 将跨域配置应用到所有路径下
        source.registerCorsConfiguration("/**", corsConfiguration);

        // 返回配置好的跨域过滤器
        return new CorsFilter(source);
    }
}
