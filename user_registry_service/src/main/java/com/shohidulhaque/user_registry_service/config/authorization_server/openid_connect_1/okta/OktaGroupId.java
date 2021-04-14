package com.shohidulhaque.user_registry_service.config.authorization_server.openid_connect_1.okta;


import com.shohidulhaque.my_people.common_model.security_model.UserSecurityAccessPrivilage;
import java.util.HashMap;
import java.util.Map;

//TODO: create as a config in application yaml
public class OktaGroupId {

    public static String CHAT =  "00g2addc39CFaS5ue5d6";
    public static String GROUP_ADMIN = "00g2aejd7vMMLee3q5d6";
    public static String POST  = "00g2ae1vlopWx8Vep5d6";

    public final static Map<String, String> GroupNameToGroupId = new HashMap<>();

    static {
        GroupNameToGroupId.put(UserSecurityAccessPrivilage.Role.CHAT.getRoleName(), CHAT);
        GroupNameToGroupId.put(UserSecurityAccessPrivilage.Role.GROUP_ADMIN.getRoleName(), GROUP_ADMIN);
        GroupNameToGroupId.put(UserSecurityAccessPrivilage.Role.POST.getRoleName(),POST);
    }

}
