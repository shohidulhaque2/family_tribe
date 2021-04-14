package com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class ContentUserMailboxSuccessResponsePayload implements Serializable {
    UserMailbox userMailBox;
}
