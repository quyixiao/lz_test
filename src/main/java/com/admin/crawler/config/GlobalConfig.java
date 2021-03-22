package com.admin.crawler.config;

import com.admin.crawler.utils.DataScopeInterceptor;
import com.lz.mybatis.plugin.config.ResolverBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {

    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }

    @Bean
    public ResolverBeanPostProcessor resolverBeanPostProcessor() {

        return new ResolverBeanPostProcessor();
    }
}

