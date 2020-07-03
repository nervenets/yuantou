package com.nervenets.general.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "application", ignoreInvalidFields = true)
@Data
public class ApplicationProperties {
    private List<String> authorizePermitAll = new ArrayList<>();
}
