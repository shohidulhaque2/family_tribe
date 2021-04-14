package com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessCode.SuccessType;
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
public class ContentUserMailboxSuccessResponse <T> implements Serializable {

    @Default
    SuccessType type = SuccessType.undefined;

    T payload;
}
