package com.shohidulhaque.my_people.common_utility_test.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;

public class TestAuthenticationManagerResolver implements AuthenticationManagerResolver {
    @Override
    public AuthenticationManager resolve(Object context) {
        return new TestAuthenticationManager();
    }
}
