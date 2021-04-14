package com.shohidulhaque.my_people.common_model.oauth2.open_id_connect;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

public class OpenIdConnectFilter extends OncePerRequestFilter {

    static final String OpenIdConnect1Token = "OpenIdConnect1Token";
    static final JwtDecoder JwtDecoder = null;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        //https://connect2id.com/products/nimbus-jose-jwt/examples/validating-jwt-access-tokens
        String accessToken =
            "eyJraWQiOiJDWHVwIiwidHlwIjoiYXQrand0IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJib2IiLCJzY" +
                "3AiOlsib3BlbmlkIiwiZW1haWwiXSwiY2xtIjpbIiFCZyJdLCJpc3MiOiJodHRwczpcL1wvZGVtby5jM" +
                "mlkLmNvbVwvYzJpZCIsImV4cCI6MTU3MTMxMjAxOCwiaWF0IjoxNTcxMzExNDE4LCJ1aXAiOnsiZ3Jvd" +
                "XBzIjpbImFkbWluIiwiYXVkaXQiXX0sImp0aSI6ImJBT1BiNWh5TW80IiwiY2lkIjoiMDAwMTIzIn0.Q" +
                "hTAdJK8AbdJJhQarjOz_qvAINQeWJCIYSROVaeRpBfaOrTCUy5gWRf8xrpj1DMibdHwQGPdht3chlAC8" +
                "LGbAorEu0tLLcOwKl4Ql-o30Tdd5QhjNb6PndOY89NbQ1O6cdOZhvV4XB-jUAXi3nDgCw3zvIn2348Va" +
                "2fOAzxUvRs2OGsEDl5d9cmL3e68YqSh7ss12y9oBDyEyz8Py7dtXgt6Tg67n9WlEBG0r4KloGDBdbCCZ" +
                "hlEyURkHaE-3nUcjwd-CEVeqWPO0bsLhwto-80j8BtsfD649GnvaMb9YdbdYhTTs-MkRUQpQIZT0s9oK" +
                "uzKayvZhk0c_0FoSeW7rw";

                // Create a JWT processor for the access tokens
                ConfigurableJWTProcessor<SecurityContext> jwtProcessor =
                    new DefaultJWTProcessor<>();

                // Set the required "typ" header "at+jwt" for access tokens issued by the
                // Connect2id server, may not be set by other servers
                jwtProcessor.setJWSTypeVerifier(
                    new DefaultJOSEObjectTypeVerifier<>(new JOSEObjectType("at+jwt")));

                // The public RSA keys to validate the signatures will be sourced from the
                // OAuth 2.0 server's JWK set, published at a well-known URL. The RemoteJWKSet
                // object caches the retrieved keys to speed up subsequent look-ups and can
                // also handle key-rollover
                JWKSource<SecurityContext> keySource =new RemoteJWKSet<>(new URL("https://demo.c2id.com/c2id/jwks.json"));

                // The expected JWS algorithm of the access tokens (agreed out-of-band)
                    JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;

                // Configure the JWT processor with a key selector to feed matching public
                // RSA keys sourced from the JWK set URL
                JWSKeySelector<SecurityContext> keySelector =
                    new JWSVerificationKeySelector<>(expectedJWSAlg, keySource);

                jwtProcessor.setJWSKeySelector(keySelector);

                // Set the required JWT claims for access tokens issued by the Connect2id
                // server, may differ with other servers
                jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier(
                    new JWTClaimsSet.Builder().issuer("https://demo.c2id.com/c2id").build(),
                    new HashSet<>(Arrays.asList("sub", "iat", "exp", "scp", "cid", "jti"))));


        final String openIdConnect1Token = request.getHeader(OpenIdConnect1Token);
        if(Objects.nonNull(openIdConnect1Token)){
            try{
                //this part might be challenging
                Jwt jwt = JwtDecoder.decode(openIdConnect1Token);
                OpenIdConnectTokenContextHolder.SetOpenIdConnectTokenContext( new OpenIdConnectTokenContext(jwt));
            }
            catch(JwtException e){
                OpenIdConnectTokenContextHolder.SetOpenIdConnectTokenContext(null);
            }

        }
        //look for openid connect token in http request
    }
}
