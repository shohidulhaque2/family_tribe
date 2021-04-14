package com.shohidulhaque.user_registry_service.properties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "email")
@Configuration
public class EmailVerificationProperties {

  @NotBlank
  String from;

  @NotBlank
  String subject;

  @NotNull
  Template template;

  @Data
  @ConfigurationProperties(prefix = "template")
  @Configuration
  public static class Template {

    @NotBlank
    String id;
  }

  @NotNull
  Verification verification;

  @Data
  @ConfigurationProperties(prefix = "verification")
  @Configuration
  public static class Verification {

    @NotNull
    EndPoint endPoint;

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "endpoint")
    public static class EndPoint {

      @NotBlank
      String scheme;
      @NotBlank
      String host;
      @NotBlank
      @Min(0)
      Integer port;
      @NotBlank
      String path;
    }
  }
}
