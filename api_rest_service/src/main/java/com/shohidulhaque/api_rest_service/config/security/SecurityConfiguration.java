package com.shohidulhaque.api_rest_service.config.security;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //  /authorize/oauth2/code/okta&nonce=5sdO8vEvlTYaqphTN4Mb0go1SZpUTLrJZ29-V3L3AFg
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(x -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("http://*"));
                    corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
                    corsConfiguration.addAllowedMethod(HttpMethod.GET);
                    corsConfiguration.addAllowedMethod(HttpMethod.POST);
                    corsConfiguration.addAllowedMethod(HttpMethod.PUT);
                    corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.addAllowedHeader("content-type");
//                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    return corsConfiguration;
        })
                .and()
                .authorizeRequests()
                //==========================================================================================================
                //allow access to static files and
                .antMatchers("/webjars/**", "/static/**").permitAll()
                //==========================================================================================================
                //allow the user to register
                //TODO: modify this. use only method level security.
//                .antMatchers("/api/v1/users/registration").permitAll()
//                .antMatchers("/api/v1/users/current_user/loggedin").permitAll()
                //==========================================================================================================
//                .anyRequest().authenticated()
                .and()
//                .oauth2Login()
//                .loginPage("/api/v1/users/login").permitAll()
//                .defaultSuccessUrl("http://localhost:3000/Overview", true);
//                .oauth2Login(
//                        oauth2 -> {
//                            oauth2.userInfoEndpoint(userInfo -> userInfo.oidcUserService(this.oidcUserService()));
//                            oauth2.loginPage("/api/v1/users/login").permitAll();
//                            oauth2.defaultSuccessUrl("http://localhost:3000/Overview", true);
//                        }
//                );
                .oauth2Login(
                        oauth2 ->
                                    oauth2
//                                            .userInfoEndpoint(userInfo -> userInfo.oidcUserService(this.oidcUserService()))
//                                            .userInfoEndpoint(userInfo -> userInfo.userAuthoritiesMapper(this.userAuthoritiesMapper()))
                                            .loginPage("/api/v1/users/login").permitAll()
                                            .defaultSuccessUrl("http://localhost:3000/Overview", true)
                );
    }

    //will not work. okta configures the resource server with its own oidcUserService, bumber : -(
//    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2Service() {
//        DefaultOAuth2UserService delegateDefaultOAuth2UserService =  new DefaultOAuth2UserService();
//        return (userRequest) -> {
//            // Delegate to the default implementation for loading a user
//            OAuth2User oAuth2User = delegateDefaultOAuth2UserService.loadUser(userRequest);
//
//            OAuth2AccessToken accessToken = userRequest.getAccessToken();
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//            // TODO
//            // 1) Fetch the authority information from the protected resource using accessToken
//            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities
//
//            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
//            OAuth2User oidcUser = new DefaultOAuth2User(mappedAuthorities, oAuth2User.getAttributes(), oAuth2User.getName());
//            return oidcUser;
//        };
//    }

    //will not work. okta configures the resource server with its own oidcUserService, bumber : -(
//    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//        final OidcUserService delegate = new OidcUserService();
//
//        return (userRequest) -> {
//            // Delegate to the default implementation for loading a user
//            OidcUser oidcUser = delegate.loadUser(userRequest);
//
//            OAuth2AccessToken accessToken = userRequest.getAccessToken();
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//            // TODO
//            // 1) Fetch the authority information from the protected resource using accessToken
//            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities
//
//            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
//            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
//
//            return oidcUser;
//        };
//    }


//    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return (authorities) -> {
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//            authorities.forEach(authority -> {
//                if (OidcUserAuthority.class.isInstance(authority)) {
//                    mappedAuthorities.add(authority);
//                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;
//                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
//                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
//                    // Map the claims found in idToken and/or userInfo
//                    // to one or more GrantedAuthority's and add it to mappedAuthorities
//                }
//                else if (OAuth2UserAuthority.class.isInstance(authority)) {
//                    mappedAuthorities.add(authority);
//                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;
//                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
//                    // Map the attributes found in userAttributes
//                    // to one or more GrantedAuthority's and add it to mappedAuthorities
//                }
//                else if(SimpleGrantedAuthority.class.isInstance(authority)){
//                    mappedAuthorities.add(authority);
//                }
//            });
//            IntStream.range(1, 10).forEach(x -> mappedAuthorities.add(new SimpleGrantedAuthority("CLAIM " + x)));
//            mappedAuthorities.add( new SimpleGrantedAuthority("CREATE_MESSAGE"));
//            mappedAuthorities.add( new SimpleGrantedAuthority("CREATE_CHAT_SPACE"));
//            mappedAuthorities.add( new SimpleGrantedAuthority("CREATE_DELETE_SPACE"));
//            return mappedAuthorities;
//        };
//    }

}
