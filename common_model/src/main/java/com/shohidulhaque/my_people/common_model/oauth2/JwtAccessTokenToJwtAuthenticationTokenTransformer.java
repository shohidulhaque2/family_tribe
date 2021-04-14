package com.shohidulhaque.my_people.common_model.oauth2;

import com.shohidulhaque.my_people.common_utility.ClaimsToGrantedAuthority;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtAccessTokenToJwtAuthenticationTokenTransformer implements Converter<Jwt, JwtAuthenticationToken> {

    //this allows the default converter to be run.
    Optional<Converter<Jwt, ? extends AbstractAuthenticationToken>> converter;
//
//    final WebClient rest = WebClient.create();

    public JwtAccessTokenToJwtAuthenticationTokenTransformer(Optional<Converter<Jwt, ? extends AbstractAuthenticationToken>> converter) {
        this.converter = converter;
    }

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        // invoke the userinfo endpoint
        Collection<GrantedAuthority> authorities = new HashSet<>();
        String name = "";
        //run the default converter
        if(this.converter.isPresent()){
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) this.converter.get().convert(jwt);
            authorities = jwtAuthenticationToken.getAuthorities();
            name = jwtAuthenticationToken.getName();
        }

        //call userinfo endpoint
        //https://dev-6167481.okta.com/oauth2/default/v1/userinfo

        Collection<? extends GrantedAuthority>  authoritiesFromClaim = ClaimsToGrantedAuthority.MapJwtOAuth2AccessTokenClaimsToGrantedAuthority(jwt);
        Collection<GrantedAuthority> finalGrantedAuthority = new HashSet<>();
        finalGrantedAuthority.addAll(authoritiesFromClaim);
        finalGrantedAuthority.addAll(authorities);
        return new UserUuidJwtAuthenticationToken("userUuid", jwt, finalGrantedAuthority, name);
    }
}
