package com.shohidulhaque.api_rest_service.jwt;

import static com.shohidulhaque.my_people.common_utility.ClaimsToGrantedAuthority.MapJwtOAuth2AccessTokenClaimsToGrantedAuthority;
import static com.shohidulhaque.my_people.common_utility.ClaimsToGrantedAuthority.MapJwtOpenIdConnect1ClaimsToGrantedAuthority;

import com.okta.spring.boot.oauth.AuthoritiesProvider;
import java.util.Collection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

//TODO: because of the way okta works, I cannot set the oidcUserService and oauth2Service. okta have provided this interface that must be implemented.
@Component
public class OpenIdConnect1AuthoritiesProvider implements AuthoritiesProvider {

    static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OpenIdConnect1AuthoritiesProvider.class);

    JwtDecoder jwtDecoder;

    @Autowired
    public OpenIdConnect1AuthoritiesProvider(JwtDecoder jwtDecoder){
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(OAuth2User user, OAuth2UserRequest userRequest) {
        Jwt jwt =  this.jwtDecoder.decode(userRequest.getAccessToken().getTokenValue());
        return MapJwtOAuth2AccessTokenClaimsToGrantedAuthority(jwt);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(OidcUser user, OidcUserRequest userRequest) {
        Jwt jwt =  this.jwtDecoder.decode(userRequest.getAccessToken().getTokenValue());
        return MapJwtOpenIdConnect1ClaimsToGrantedAuthority(user, jwt);
    }

}
