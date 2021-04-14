package com.shohidulhaque.my_people.storage_service.config.security;

import com.shohidulhaque.my_people.common_model.oauth2.JwtAccessTokenToJwtAuthenticationTokenTransformer;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

//TODO: add swagger
//https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@EnableWebSecurity
public class WebSecurityEnabled extends WebSecurityConfigurerAdapter {

    ApplicationContext applicationContext;

    @Autowired
    public WebSecurityEnabled(
            ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

//  private static class MyConverter implements Converter<Jwt, JwtAuthenticationToken> {
//
//    Converter<Jwt, ? extends AbstractAuthenticationToken> converter;
//
//    final WebClient rest = WebClient.create();
//
//    public MyConverter(Converter<Jwt, ? extends AbstractAuthenticationToken> converter){
//      this.converter = converter;
//    }
//
//    @Override
//    public JwtAuthenticationToken convert(Jwt jwt) {
//      // invoke the userinfo endpoint
//      JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)this.converter.convert(jwt);
//      Collection<GrantedAuthority> authorities = jwtAuthenticationToken.getAuthorities();
//      String name = jwtAuthenticationToken.getName();
//      //call userinfo endpoint
//      //https://dev-6167481.okta.com/oauth2/default/v1/userinfo
//      //
//      return new JwtAuthenticationToken(jwt, authorities, name);
//    }
//  }

    Optional<Converter<Jwt, ? extends AbstractAuthenticationToken>> getJwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter;
        if (this.applicationContext.getBeanNamesForType(JwtAuthenticationConverter.class).length > 0) {
            //what if their are multiple types found?
            jwtAuthenticationConverter = this.applicationContext.getBean(JwtAuthenticationConverter.class);
        } else {
            jwtAuthenticationConverter = new JwtAuthenticationConverter();
        }
        return Optional.of(jwtAuthenticationConverter);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .formLogin().disable()
                .cors()
                .and()
                .csrf().disable()
//                .authorizeRequests()
//        .antMatchers("/user/registration_rest_api").permitAll()
//        .antMatchers("/greetings/messsage").permitAll()
                //TODO: remove only use method based security only
//                .antMatchers("/user/registration").permitAll()
//                .antMatchers("/user/registration/*").permitAll()
//               .antMatchers("/user/activation/*").permitAll()
//                .antMatchers("/static/**").permitAll()
//                .antMatchers("/webjars/**").permitAll()
//                .antMatchers("/favicon.ico").permitAll()
//                .antMatchers("/").permitAll()
//        .anyRequest().permitAll()
//                .and()
//        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(new JwtAccessTokenToJwtAuthenticationTokenTransformer(getJwtAuthenticationConverter()));


    }

    //TODO: cannot get this work. The spring lib and OK use different rounds. find a library where rounds match
    //OKTA - USE A 10 ROUNDS
    final static int LOG_ROUNDS = 10;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(LOG_ROUNDS);

    }

}
