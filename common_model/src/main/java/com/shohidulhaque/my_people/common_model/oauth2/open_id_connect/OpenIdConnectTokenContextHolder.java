package com.shohidulhaque.my_people.common_model.oauth2.open_id_connect;

import java.util.Optional;

public class OpenIdConnectTokenContextHolder {

    private OpenIdConnectTokenContextHolder(){}
    static ThreadLocal<OpenIdConnectTokenContext> OpenIdConnectTokenContext = new ThreadLocal<>();

    public static Optional<OpenIdConnectTokenContext> SetOpenIdConnectTokenContext(OpenIdConnectTokenContext openIdConnectTokenContext){
        OpenIdConnectTokenContext.set(openIdConnectTokenContext);
        return Optional.of(openIdConnectTokenContext);
    }

    public static Optional<OpenIdConnectTokenContext> GetOpenIdConnectTokenContext(){
        return Optional.of(OpenIdConnectTokenContext.get());
    }
}
