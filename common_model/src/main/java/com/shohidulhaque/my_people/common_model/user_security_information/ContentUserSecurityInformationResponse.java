package com.shohidulhaque.my_people.common_model.user_security_information;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ContentUserSecurityInformationResponse {
    ResponseType responseType;
    List<ContentUserSecurityInformationErrorResponse> error;
    List<ContentUserSecurityInformationSuccessResponse> success;

}
