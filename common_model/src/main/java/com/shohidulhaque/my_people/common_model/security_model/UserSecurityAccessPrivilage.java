package com.shohidulhaque.my_people.common_model.security_model;

/**
 * Describes all the roles and permission any user in the system can have.
 * In otherwords, this is a security model of ALL the kinds of users in the system and their permissions.
 *
 * Any external services, including the openid connect services, or front end application ,such as a
 * native or javascript (React) application, must match or be based on this security model.
 */
public enum UserSecurityAccessPrivilage {

    PERSON( new Role[]{Role.POST} , new Permission[]{Permission.CHAT_WITH_ANOTHER_PERSON}),
    GROUP_ADMIN(new Role[]{Role.GROUP_ADMIN}, new Permission[]{});

    public enum Role {

        CHAT("CHAT", Permission.CHAT_WITH_ANOTHER_PERSON),

        POST("POST", Permission.CREATE_POST,
                                    Permission.READ_POST,
                                    Permission.DELETE_POST,
                                    Permission.UPDATE_POST),

        GROUP_ADMIN("GROUP_ADMIN", Permission.CREATE_GROUP,
                                    Permission.DELETE_GROUP,
                                    Permission.ADD_PERSON_TO_GROUP,
                                    Permission.REMOVE_PERSON_FROM_GROUP,
                                    Permission.DELETE_POST_ON_GROUP,
                                    Permission.INVITE_PERSON);

        final String roleName;
        final Permission[] permissions;

        public String getRoleName() {
            return roleName;
        }

        public Permission[] getPermissions() {
            return permissions;
        }

        Role(String roleName, Permission ...permissions){
            this.roleName = roleName;
            this.permissions = permissions;
        }
    }

    public enum Permission {
        CREATE_POST("create_post"),
        READ_POST("read_post"),
        DELETE_POST("delete_post"),
        UPDATE_POST("update_post"),

        CHAT_WITH_ANOTHER_PERSON("chat_with_another_person"),

        CREATE_GROUP("create_group"),
        DELETE_GROUP("delete_group"),
        ADD_PERSON_TO_GROUP("add_person_to_group"),
        REMOVE_PERSON_FROM_GROUP("remove_person_from_group"),
        DELETE_POST_ON_GROUP("delete_post_on_group"),
        INVITE_PERSON("invite_person");

        Permission(String permission){
            this.permission = permission;
        }

        public String getPermission() {
            return permission;
        }

        final String permission;
    }

    public Role[] getRoles() {
        return roles;
    }

    public Permission[] getPermissions() {
        return permissions;
    }

    final Role[] roles;
    final Permission [] permissions;
    UserSecurityAccessPrivilage(Role [] roles, Permission [] permission){
        this.roles = roles;
        this.permissions = permission;
    }

    public enum OAuth2Scope {
    }

    public enum OpenIdConnect1Scope {

        OpenIdConnect1Scope("openid");

        String scope;
        OpenIdConnect1Scope(String scope){
            this.scope = scope;
        }
    }
}
