package com.nervenets.general.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nervenets.general.jwt.AuthenticationInterceptor;
import com.nervenets.general.service.GlobalSecurityService;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Joe on 2018/1/8.
 */
@Configuration
public class ApplicationConfiguration {
    @Reference(version = "1.0.0", check = false)
    private GlobalSecurityService globalSecurityService;
    @Resource
    private ApplicationProperties applicationProperties;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                        .maxAge(3600)
                        .allowCredentials(true);
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authenticationInterceptor())
                        .excludePathPatterns(applicationProperties.getAuthorizePermitAll())
                        .excludePathPatterns("/swagger-ui.html")
                        .excludePathPatterns("/webjars/**")
                        .excludePathPatterns("/swagger-resources/**")
                        .excludePathPatterns("/error**")
                        .excludePathPatterns("/v2/**");
            }
        };
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor(globalSecurityService);
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);        // 设置核心线程数
        executor.setMaxPoolSize(1000);        // 设置最大线程数
        executor.setQueueCapacity(100);      // 设置队列容量
        executor.setKeepAliveSeconds(120);   // 设置线程活跃时间（秒）
        executor.setThreadNamePrefix("user-rpt-");  // 设置默认线程名称
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());// 设置拒绝策略
        executor.setWaitForTasksToCompleteOnShutdown(true); // 等待所有任务结束后再关闭线程池
        return executor;
    }

    @Bean
    Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
