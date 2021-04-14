package com.shohidulhaque.user_registry_service.properties;

import java.time.temporal.ChronoUnit;
import javax.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "account.activation.token.validity")
@Data
public class AccountActivationProperties {

  ChronoUnit unit = ChronoUnit.MINUTES;
  @Min(0)
  Integer time;
}
