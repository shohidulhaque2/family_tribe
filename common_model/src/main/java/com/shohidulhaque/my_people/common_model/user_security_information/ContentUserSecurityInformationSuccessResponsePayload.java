package com.shohidulhaque.my_people.common_model.user_security_information;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ContentUserSecurityInformationSuccessResponsePayload {
    Role[] role;
    OpenIdClaim[] openIdClaim;
    OAuth2Scope [] oauth2Scope;
    String userId;
}
