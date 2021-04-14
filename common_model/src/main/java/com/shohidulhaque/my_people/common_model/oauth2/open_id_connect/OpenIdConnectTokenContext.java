package com.shohidulhaque.my_people.common_model.oauth2.open_id_connect;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.oauth2.jwt.Jwt;

@Data
@AllArgsConstructor
public class OpenIdConnectTokenContext {
    Jwt jwt;
}
