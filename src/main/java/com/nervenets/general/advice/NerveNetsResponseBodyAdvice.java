package com.nervenets.general.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nervenets.general.utils.AESUtils;
import com.nervenets.general.utils.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 封装返回结果，AES加密
 */
@Profile({"aes"})
@Order(1)
@Configuration
@ControllerAdvice(basePackages = "com.nervenets")
@Slf4j
public class NerveNetsResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if ("application/json".equalsIgnoreCase(selectedContentType.toString())) {
            ObjectMapper mapper = new ObjectMapper();

            MappingJacksonValue jacksonValue = getOrCreateContainer(body);
            String value = mapper.writeValueAsString(jacksonValue.getValue());

            String ivSourceKey = StringUtils.randomString(16);
            String mergeKey = StringUtils.randomString(1);

            String ivKey = ivSourceKey.replaceAll("[0-9]", mergeKey);

            //Base64后数据大小几乎翻倍，不考虑
            //return Base64Utils.encodeToString((mergeKey + ivSourceKey + "#" + AESUtils.encryptData(String.valueOf(body), ivKey, ivKey)).getBytes());
            return mergeKey + ivSourceKey + "/" + AESUtils.encryptData(value, ivKey, ivKey);
        }
        return body;
    }

    protected MappingJacksonValue getOrCreateContainer(Object body) {
        return (body instanceof MappingJacksonValue ? (MappingJacksonValue) body : new MappingJacksonValue(body));
    }
}
