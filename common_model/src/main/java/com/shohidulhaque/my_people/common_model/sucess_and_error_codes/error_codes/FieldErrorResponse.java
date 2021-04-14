package com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
@Data
public class FieldErrorResponse extends ErrorResponseBase {
    private  String objectName;
    private  FieldError fieldError;
    @Default
    private  ErrorCode.ErrorType type = ErrorType.undefined;
}
