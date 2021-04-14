package com.shohidulhaque.user_registry_service.service.registration.listener;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedPersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.model.PersonRegistration;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenIdConnect2Proxy {

    Client oktaOpenIdConnect1Client;

    @Autowired
    public OpenIdConnect2Proxy(Client oktaOpenIdConnect1Client){
        this.oktaOpenIdConnect1Client = oktaOpenIdConnect1Client;
    }

    public User registerUserWithOAuthServer(ValidatedPersonRegistrationDtoRequest personRegistration, String password, Set<String> roleNames, UUID uuidOfPerson)
    throws RuntimeException {
        return UserBuilder.instance()
            .setEmail(personRegistration.getEmail())
            .setFirstName(personRegistration.getFirstName())
            .setLastName(personRegistration.getLastName())
            .setPassword(password.toCharArray())
            .setGroups(roleNames)
            .setActive(false)
            .setProfileProperties(Map.of("UUID", uuidOfPerson.toString()))
            .buildAndCreate(this.oktaOpenIdConnect1Client);
    }

    public User getUser(PersonRegistration newPersonRegistration)  throws RuntimeException  {
        return oktaOpenIdConnect1Client.getUser(newPersonRegistration.getIdentityServerUserId());
    }
}
