package com.shohidulhaque.user_registry_service.properties;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("authorizationserver.openidconnect1.client")
public class OktaOpenConnect1ClientProperties {
    @NotBlank
    String domain;
    @NotBlank
    String token;
}
