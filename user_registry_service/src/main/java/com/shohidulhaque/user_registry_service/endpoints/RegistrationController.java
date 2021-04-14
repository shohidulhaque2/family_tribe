package com.shohidulhaque.user_registry_service.endpoints;

import com.shohidulhaque.user_registry_service.properties.AccountActivationProperties;
import com.shohidulhaque.user_registry_service.service.exception.UserRegistryActivationExiredException;
import com.shohidulhaque.user_registry_service.service.exception.UserRegistryRegistryActivationDoesNotExistException;
import com.shohidulhaque.user_registry_service.service.registration.RegistrationService;
import java.util.Base64;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Log
public class RegistrationController {

  @Autowired
  public RegistrationController(RegistrationService registrationService,
      AccountActivationProperties accountActivationProperties) {
    this.registrationService = registrationService;
    this.accountActivationProperties = accountActivationProperties;
  }

  RegistrationService registrationService;
  AccountActivationProperties accountActivationProperties;

  @GetMapping("/user/activation/{activation_token}")
  public String activateAccount(@PathVariable("activation_token") String activationToken) {
    try {
      String decodedActivationToken = new String(Base64.getDecoder().decode(activationToken));
      this.registrationService.activateAccount(decodedActivationToken);
      //TODO: call other services to create resources eagerly
    } catch (UserRegistryRegistryActivationDoesNotExistException e) {
      log.info(e.getMessage());
      return "accountActivationFailure";
    } catch (UserRegistryActivationExiredException e) {
      log.info(e.getMessage());
      return "accountActivationFailure";
    }
    return "accountActivated";
  }

}
