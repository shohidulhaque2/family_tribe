package com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class UserMailbox implements Serializable {

    @NotNull
    UUID userUuid;

    @NotNull
    Instant creationTime;

    @NotNull
    UserMail[] mail;

}
