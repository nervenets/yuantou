package com.nervenets.general.config;

import com.alibaba.fastjson.JSON;
import com.nervenets.general.Global;
import com.nervenets.general.enumeration.Platform;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Profile({"swagger"})
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    @Value("${app.base.domain}")
    private String host;

    @Bean
    public Docket buildDocket() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder().name(Global.Constants.TOKEN_KEY)
                .description("登录标识")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .build());
        parameters.add(new ParameterBuilder().name(Global.Constants.PLATFORM_KEY)
                .description("接口请求平台，值：" + JSON.toJSONString(Platform.values()))
                .modelRef(new ModelRef("string"))
                .parameterType("header").defaultValue(Platform.pc.name())
                .build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .host(host)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nervenets"))//要扫描的API(Controller)基础包
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("For Api")
                .termsOfServiceUrl("http://www.nervenets.cn")
                .licenseUrl("http://www.nervenets.cn")
                .contact("Joe")
                .version("1.0")
                .build();
    }
}
