package com.nervenets.general;

import com.nervenets.general.entity.MessageCode;
import com.nervenets.general.entity.ResponseResult;
import com.nervenets.general.exception.NerveNetsGeneralException;
import com.nervenets.general.exception.TokenIllegalException;
import com.nervenets.general.service.MailService;
import com.nervenets.general.service.RedisService;
import com.nervenets.general.utils.ClassUtils;
import com.nervenets.general.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {
    @Autowired
    private MailService mailService;
    @Autowired
    private Environment environment;
    @Resource
    private RedisService redisService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();

        try {
            final Object target = result.getTarget();
            String[] fields = field.split("\\.");
            ApiModelProperty property = ClassUtils.getDeclaredField(target.getClass(), fields).getAnnotation(ApiModelProperty.class);

            if (null != property) field = property.value();
        } catch (NoSuchFieldException ignored) {

        }

        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseResult.failed(MessageCode.code_400, message));
    }

    /**
     * 所有异常报错
     *
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> allExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String message = ex.getMessage();
        if (ex instanceof MissingServletRequestParameterException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseResult.failed(MessageCode.code_400, "缺少请求参数:" + message));
        } else if (ex instanceof HttpMessageNotReadableException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseResult.failed(MessageCode.code_400, "参数解析失败:" + message));
        } else if (ex instanceof ServletException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseResult.failed(MessageCode.code_400, message));
        } else if (ex instanceof IOException) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        } else if (ex instanceof ValidationException) {
            if (null != ex.getCause() && ex.getCause() instanceof TokenIllegalException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseResult.failed(MessageCode.code_401, ex.getCause().getMessage()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseResult.failed(MessageCode.code_400, message));
        } else if (ex instanceof TokenIllegalException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseResult.failed(MessageCode.code_401, message));
        } else if (ex instanceof NerveNetsGeneralException) {
            int code = ((NerveNetsGeneralException) ex).getCode();
            return ResponseEntity.status(code).body(ResponseResult.failed(code, ex.getMessage()));
        } else {
            ex.printStackTrace();
            String domain = environment.getProperty("app.base.domain", "localhost:9090");
            if (!domain.contains("localhost")) {
                if (redisService.canProceedCustom(ex.getClass().getSimpleName(), 60)) {
                    mailService.exceptionEmailSend(environment.getProperty("spring.application.name"), "接口", requestInfo(request), ex);
                }
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseResult.failed(MessageCode.code_500, message));
        }
    }

    private String requestInfo(HttpServletRequest request) {
        StringBuilder content = new StringBuilder();
        content.append("URL ：").append(request.getRequestURL()).append("<br>");
        content.append("<br>headers ：<br>");
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            content.append(key).append(":").append(value).append("<br>");
        }
        content.append("<br>params ：<br>");
        for (Object key : request.getParameterMap().keySet()) {
            content.append(key).append(":").append(request.getParameter(String.valueOf(key))).append("<br>");
        }
        content.append("<br>body ：<br>");
        content.append(StringUtils.readRequestBody(request)).append("<br><br>");

        return content.toString();
    }
}
