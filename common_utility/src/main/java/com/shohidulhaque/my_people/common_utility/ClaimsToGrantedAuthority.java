package com.shohidulhaque.my_people.common_utility;

import com.nimbusds.jose.shaded.json.JSONArray;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;

public class ClaimsToGrantedAuthority {

    static String UUID = "uuid";
    static String PERMISSION_CLAIMS_PREFIX = "PERMISSION_";
    static String ROLE_CLAIM_PREFIX = "ROLE_";

    /**
     * Convert claims in the JWT token, which represent which groups and permissions the user has, to
     * scopes that the oidc client can use to access and change the resource owner's resources. Its a way of
     * transferring the resource owner's permission to the oidc client.
     * @param jwt The claims in the user's access token.
     * @return a set of granted authorities that represent the actions the oidc client can use to access and change the resource owner's resources.
     */
    public static Collection<? extends GrantedAuthority> MapJwtOAuth2AccessTokenClaimsToGrantedAuthority(Jwt jwt){
        Map<String, Object> claims = jwt.getClaims();
        Collection<GrantedAuthority> authorities = new HashSet<>();
        //hack. custom claims in the access token should appear in the Authentication, but does not
        //the only access i seem to have to the access token is here according to okta.
        //the users uuid will be placed here as an authority. but, this is a hack. it should appear in the spring
        //authentication token. the situation should change when i change to keycloak.
        if(Objects.nonNull(claims.get("uuid")) &&  claims.get("uuid") instanceof String){
            authorities.add(new UuidGrantedAuthority((String)claims.get("uuid")));
        }

        claims.forEach((claim, claimValue) -> {
            if(claim.startsWith(ROLE_CLAIM_PREFIX)){
                authorities.add( new SimpleGrantedAuthority(claim.toUpperCase()));
            }
            else if(claim.startsWith(PERMISSION_CLAIMS_PREFIX)){
                JSONArray value = (JSONArray) claimValue;
                value.forEach( group -> {
                    authorities.add( new SimpleGrantedAuthority( "ROLE_" + (((String) group).toUpperCase()) + "_" + claim));
                    authorities.add( new SimpleGrantedAuthority( "ROLE_" + ((String) group).toUpperCase()));
                });
            }
        });
        return authorities;
    }

    public static Collection<? extends GrantedAuthority> MapJwtOpenIdConnect1ClaimsToGrantedAuthority(OidcUser user, Jwt jwtAccessToken){
        List<String> groups = (List<String>) user.getUserInfo().getClaims().get("groups");
        Collection<GrantedAuthority> authorities = new HashSet<>();
        groups.forEach( group -> {
            var g = new SimpleGrantedAuthority( "ID_ROLE_" + group.toUpperCase());
            authorities.add(g);
        });
        authorities.addAll(MapJwtOAuth2AccessTokenClaimsToGrantedAuthority(jwtAccessToken));
        return authorities;
    }

}
