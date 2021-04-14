package com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service;

import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
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
public class ContentMailbox implements Serializable {

    @Default
    ResponseType responseType = new ResponseType("",Undefined.undefined.getCode(), Undefined.undefined
        .getSubCode());

    @Default
    ContentUserMailboxErrorResponse[] error = {};

    @Default
    ContentUserMailboxSuccessResponse[] success = {};
}
