package com.nervenets.general.config;

import com.nervenets.general.filter.NerveNetsRequestWrapperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 2020/6/23 17:52 created by Joe
 **/
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<NerveNetsRequestWrapperFilter> someFilterRegistration() {
        FilterRegistrationBean<NerveNetsRequestWrapperFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(nerveNetsRequestWrapperFilter());
        registration.addUrlPatterns("*");
        registration.setName("nerveNetsRequestWrapperFilter");
        return registration;
    }


    @Bean(name = "nerveNetsRequestWrapperFilter")
    public NerveNetsRequestWrapperFilter nerveNetsRequestWrapperFilter() {
        return new NerveNetsRequestWrapperFilter();
    }
}
