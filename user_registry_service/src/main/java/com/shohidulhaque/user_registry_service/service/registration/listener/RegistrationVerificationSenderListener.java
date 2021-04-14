package com.shohidulhaque.user_registry_service.service.registration.listener;

import com.shohidulhaque.user_registry_service.model.PersonVerificationToken;
import com.shohidulhaque.user_registry_service.service.registration.listener.event.OnRegistrationCompleteEvent;
import com.shohidulhaque.user_registry_service.service.user_invitation.MailSender;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Log
public class RegistrationVerificationSenderListener implements
    ApplicationListener<OnRegistrationCompleteEvent> {

  @Autowired
  public RegistrationVerificationSenderListener(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  MailSender mailSender;

  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);

  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) {
    PersonVerificationToken personVerificationToken = event.getVerificationToken();
    log.info("received event for sending an email to activate a user account.");
    this.mailSender.sendInvite(personVerificationToken);
  }

}
