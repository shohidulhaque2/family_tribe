package com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.FieldErrorResponse;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonInclude(Include.NON_NULL)
public class ContentUserMailboxErrorResponse implements Serializable {
    //generally this is always ignored, can be null
    @Default
    ErrorType type = ErrorType.undefined;

    @Default
    FieldErrorResponse [] payload = {};
}
