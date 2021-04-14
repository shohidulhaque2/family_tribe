package com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service;

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
@Builder
@ToString
public class ContentUserMailboxErrorResponsePayload implements Serializable {
    @Default
    FieldErrorResponse [] error = {};
}
