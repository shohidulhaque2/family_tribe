package com.shohidulhaque.my_people.common_utility_test.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class TestAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(JwtFactory.Jwt());;
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }
}
