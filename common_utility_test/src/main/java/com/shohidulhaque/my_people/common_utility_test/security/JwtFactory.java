package com.shohidulhaque.my_people.common_utility_test.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtFactory {

    //TODO: create a legimate JWT token
    public static  Instant SomeTime = null;
    public static  Instant OneYearFromSomeTime = null;
    public static  String Subject = null;
    public static  String Jwt_Algorithm = null;
    public static  String TokenValue = null;
    public static  Jwt Jwt = null;


    static {
        LocalDateTime SomeDateTime = LocalDateTime.now();
        LocalDateTime OneYearFromSomeDateTime = LocalDateTime.from(SomeDateTime).plusYears(1);
        SomeTime = SomeDateTime.toInstant(ZoneOffset.UTC);
        OneYearFromSomeTime = OneYearFromSomeDateTime.toInstant(ZoneOffset.UTC);
        Subject = "abdul_barr@abdul_barr.com";
        Jwt_Algorithm = "none";
        TokenValue = "token";
        Jwt = new Jwt(TokenValue, SomeTime, OneYearFromSomeTime, Map.of("alg", Jwt_Algorithm), Map.of("sub", Subject));
    }

    public static Jwt Jwt(){
        return Jwt;
    }
}
