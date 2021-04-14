package com.shohidulhaque.my_people.common_model.oauth2;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class UserUuidJwtAuthenticationToken extends JwtAuthenticationToken {

    String userUuid;

    public UserUuidJwtAuthenticationToken(String userUuid, Jwt jwt) {

        super(jwt);
        this.userUuid = userUuid;
    }

    public UserUuidJwtAuthenticationToken(String userUuid, Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        super(jwt, authorities);
        this.userUuid = userUuid;
    }

    public UserUuidJwtAuthenticationToken(String userUuid, Jwt jwt, Collection<? extends GrantedAuthority> authorities, String name) {
        super(jwt, authorities, name);
        this.userUuid = userUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }
}
