package com.shohidulhaque.user_registry_service.config.authorization_server.openid_connect_1.okta;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.shohidulhaque.user_registry_service.config.constants.HttpTransportScheme;
import com.shohidulhaque.user_registry_service.properties.OktaOpenConnect1ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;



@Configuration
public class OktaOpenConnect1ClientConfiguration {
    //TODO: update to OAuth 2.0
    @Bean
    public Client oktaOpenIdConnect1Client(OktaOpenConnect1ClientProperties oktaOpenConnect1ClientProperties) {
        final String OKTA_OPENID_CONNECT1_AUTHORIZATION_SERVER = UriComponentsBuilder
                .newInstance()
                .scheme(HttpTransportScheme.HTTPS)
                .host(oktaOpenConnect1ClientProperties.getDomain())
                .build()
                .toUriString();
        Client client = Clients.builder()
                .setOrgUrl(OKTA_OPENID_CONNECT1_AUTHORIZATION_SERVER)
                .setClientCredentials(new TokenClientCredentials(oktaOpenConnect1ClientProperties.getToken()))
                .build();
        return client;
    }

}
