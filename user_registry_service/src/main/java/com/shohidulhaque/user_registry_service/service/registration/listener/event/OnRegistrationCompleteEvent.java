package com.shohidulhaque.user_registry_service.service.registration.listener.event;

import com.shohidulhaque.user_registry_service.model.PersonVerificationToken;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

  public OnRegistrationCompleteEvent(PersonVerificationToken personVerificationToken) {
    super(personVerificationToken);
  }

  public PersonVerificationToken getVerificationToken() {
    return (PersonVerificationToken) getSource();
  }

}
