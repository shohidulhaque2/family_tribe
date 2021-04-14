package com.shohidulhaque.my_people.common_model.user_security_information;

import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode;
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
public class ContentUserSecurityInformationErrorResponse {

    ErrorCode.ErrorType type;
    ContentUserSecurityInformationErrorResponsePayload payload;

}
