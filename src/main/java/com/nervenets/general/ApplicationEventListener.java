package com.nervenets.general;

import com.nervenets.general.jwt.util.JwtUtils;
import com.nervenets.general.utils.JodaUtils;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.AsyncConsumerRestartedEvent;
import org.springframework.amqp.rabbit.listener.AsyncConsumerStartedEvent;
import org.springframework.amqp.rabbit.listener.AsyncConsumerStoppedEvent;
import org.springframework.amqp.rabbit.listener.ConsumeOkEvent;
import org.springframework.boot.context.event.*;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import springfox.documentation.schema.configuration.ObjectMapperConfigured;

import java.util.Arrays;


/**
 * SPRING-BOOT  生命周期监听器
 *
 * @author Administrator
 */
public class ApplicationEventListener implements ApplicationListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) { // 初始化环境变量
            logger.info("###初始化环境变量");
        } else if (event instanceof ApplicationPreparedEvent) { // 初始化完成
            logger.info("###初始化完成");
        } else if (event instanceof ContextRefreshedEvent) { // 应用刷新
            logger.info("###应用刷新");
        } else if (event instanceof ApplicationReadyEvent) {// 应用启动准备就绪
            logger.info("###应用启动准备就绪");
        } else if (event instanceof ContextStartedEvent) { // 应用启动，需要在代码动态添加监听器才可捕获
            logger.info("###应用启动，需要在代码动态添加监听器才可捕获");
        } else if (event instanceof ContextStoppedEvent) { // 应用停止
            logger.info("###应用停止");
        } else if (event instanceof ContextClosedEvent) { // 应用关闭
            //SpringUtil.getBean(FlowConsumer.class).setState(-1);
            logger.info("###应用关闭");
        } else if (event instanceof ServletRequestHandledEvent) {
            ServletRequestHandledEvent handledEvent = (ServletRequestHandledEvent) event;
            logger.info(JodaUtils.timeLongToString() + "," + handledEvent.getRequestUrl() + ",taken:" + handledEvent.getProcessingTimeMillis() + "ms,method:" + handledEvent.getMethod());
        } else if (event instanceof AsyncConsumerRestartedEvent) {
            AsyncConsumerRestartedEvent restartedEvent = (AsyncConsumerRestartedEvent) event;
            logger.error("###消息队列服务似乎重启连接不上了？？" + restartedEvent.toString());
        } else if (event instanceof ApplicationFailedEvent) {
            ApplicationFailedEvent failedEvent = (ApplicationFailedEvent) event;
            logger.error("###应用启动失败", failedEvent.getException());
        } else if (event instanceof AsyncConsumerStoppedEvent) {
            AsyncConsumerStoppedEvent stoppedEvent = (AsyncConsumerStoppedEvent) event;
            logger.error("###消息队列消费者已停止" + stoppedEvent.toString());
        } else if (event instanceof AsyncConsumerStartedEvent) {
            AsyncConsumerStartedEvent startedEvent = (AsyncConsumerStartedEvent) event;
            logger.info("###消息队列消费者开始启动" + startedEvent.toString());
        } else if (event instanceof ConsumeOkEvent) {
            ConsumeOkEvent consumeOkEvent = (ConsumeOkEvent) event;
            logger.info("###消息队列消费者已就续" + consumeOkEvent.toString());
        } else if (event instanceof ServletWebServerInitializedEvent) {
            logger.info("###应用初始化成功！");
            logger.info("###检查权限冲突...");
            JwtUtils.getAllMenuRoles();
        } else if (event instanceof ApplicationStartedEvent) {
            ConfigurableEnvironment environment = ((ApplicationStartedEvent) event).getApplicationContext().getEnvironment();
            final String[] activeProfiles = environment.getActiveProfiles();
            logger.info("###恭喜您，应用启动成功！" + Arrays.toString(activeProfiles));
            logger.info(String.format("http://%s/swagger-ui.html", environment.getProperty("app.base.domain")));
        } else if (event instanceof ObjectMapperConfigured) {
            logger.info("###对象配置完成");
        } else {
            logger.info("###未知" + event.toString());
        }

    }

}
