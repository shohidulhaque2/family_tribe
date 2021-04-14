package com.shohidulhaque.my_people.common_utility;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@ToString
@EqualsAndHashCode
public class UuidGrantedAuthority implements GrantedAuthority {

    final String uuid;

    public UuidGrantedAuthority(String uuid){
        this.uuid = uuid;
    }

    String getUuid(){
        return uuid;
    }

    @Override
    public String getAuthority() {
        return uuid;
    }
}
