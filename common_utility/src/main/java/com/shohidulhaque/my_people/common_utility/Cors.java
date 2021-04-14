package com.shohidulhaque.my_people.common_utility;

import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;

public class Cors {
    public static CorsConfiguration GetDefaultCorsConfiguration(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of("http://*"));
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("content-type");
//      orsConfiguration.setAllowedHeaders(List.of("*"));
        return corsConfiguration;
    }
}
