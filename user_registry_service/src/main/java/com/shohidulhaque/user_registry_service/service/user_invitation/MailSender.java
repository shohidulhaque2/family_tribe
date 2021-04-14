package com.shohidulhaque.user_registry_service.service.user_invitation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.shohidulhaque.user_registry_service.model.PersonVerificationToken;
import com.shohidulhaque.user_registry_service.properties.EmailVerificationProperties;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

@Log
public class MailSender {

  private static final String SEND_GRID_API_KEY = "spring.sendgrid.api-key";
  SendGrid sendGridClient;
  Environment environment;
  EmailVerificationProperties emailVerificationProperties;

  @Autowired
  public MailSender(Environment environment,
      EmailVerificationProperties emailVerificationProperties) {
    this.environment = environment;
    this.emailVerificationProperties = emailVerificationProperties;
  }

  @PostConstruct
  public void sendGridClient() {
    final String sendGridApikey = this.environment.getProperty(SEND_GRID_API_KEY);
    if (!StringUtils.hasText(sendGridApikey)) {
      throw new IllegalArgumentException(
          "Could not resolve placeholder 'spring.sendgrid.api-key'.");
    }
    this.sendGridClient = new SendGrid(sendGridApikey);
  }

  //HELPER CLASS NEEDED. SENDGRID 4.0.1 USED BY SPRING BOOT DOES NOT HELP CREATE THE BODY FOR
  //A DYNAMIC TEMPLATE.
  //TODO: CHANGE LIBRARY TO VERSION TO 4.7.0. ALL ITS DEPENDENCIES ARE COMPATIABLE WITH THE VERSIONS USED BY SPRING BOOT 2.0
  //PROBABLY WISE TO USE OSGI TO SEPERATE SERVICES COMPLETELY. AVOIDS JAR HELL. THIS IS A
  //FUTURE PROJECT.
  static class DynamicTemplatePersonalization extends Personalization {

    @JsonProperty(value = "dynamic_template_data")
    private Map<String, Object> dynamicTemplateData;

    @JsonProperty("dynamic_template_data")
    public Map<String, Object> getDynamicTemplateData() {
      if (dynamicTemplateData == null) {
        return Collections.<String, Object>emptyMap();
      }
      return dynamicTemplateData;
    }

    public void addDynamicTemplateData(String key, Object value) {
      if (dynamicTemplateData == null) {
        dynamicTemplateData = new HashMap<String, Object>();
        dynamicTemplateData.put(key, value);
      } else {
        dynamicTemplateData.put(key, value);
      }
    }

  }

  private String getActivationUrl(PersonVerificationToken personVerificationToken) {
    return UriComponentsBuilder
        .newInstance()
        .scheme(this.emailVerificationProperties.getVerification().getEndPoint().getScheme())
        .host(this.emailVerificationProperties.getVerification().getEndPoint().getHost())
        .port(this.emailVerificationProperties.getVerification().getEndPoint().getPort())
        .path(this.emailVerificationProperties.getVerification().getEndPoint().getPath() + "/"
            + Base64.getEncoder().encodeToString(personVerificationToken.getToken().getBytes()))
        .build()
        .toString();
  }


  private void addPersonalisationDataToTemplate(DynamicTemplatePersonalization personalization,
      PersonVerificationToken personVerificationToken) {
    personalization.addDynamicTemplateData("Sender_Name", "Shohidul Haque");
    personalization.addDynamicTemplateData("Sender_Address", "Lapland");
    personalization.addDynamicTemplateData("Sender_City", "London");
    personalization.addDynamicTemplateData("Sender_State", "London");
    personalization.addDynamicTemplateData("Sender_Zip", "E14 3DP");
    String activationLinkUri = getActivationUrl(personVerificationToken);
    personalization.addDynamicTemplateData("Activation_Link", activationLinkUri);
  }

  public void sendInvite(PersonVerificationToken personVerificationToken) {

    Email from = new Email(this.emailVerificationProperties.getFrom());
    String subject = this.emailVerificationProperties.getSubject();
    Email to = new Email(personVerificationToken.getPersonRegistration().getEmail());
    Mail mail = new Mail();
    mail.setFrom(from);
    mail.setSubject(subject);
    mail.setTemplateId(this.emailVerificationProperties.getTemplate().getId());

    DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
    personalization.addTo(to);
    //use a factory design pattern for the personalisation. could be different for every email template
    addPersonalisationDataToTemplate(personalization, personVerificationToken);

    mail.addPersonalization(personalization);

    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      //GDPR ISSUE IF USER EMIAL IS ADDED HERE
      log.info("sending email to " + personVerificationToken.getPersonRegistration().getEmail());
      Response response = sendGridClient.api(request);
      log.info("response status code from sendgrid: " + response.getStatusCode());
      log.info("response body from sendgrid: " + response.getBody());
      log.info("response headers from: " + response.getHeaders());
    } catch (IOException ex) {
      //GDPR ISSUE IF USER EMIAL IS ADDED HERE
      log.info("sending of email to " + personVerificationToken.getPersonRegistration().getEmail()
          + " has caused an exception.");
      log.info(ex.getMessage());
    }
  }

}
