package com.shohidulhaque.user_mailbox_service.data_model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@Entity
@DiscriminatorValue(Mailbox.InvitationMail)
public class InvitationMail extends Mail{

//    @org.hibernate.annotations.Type(type="uuid-char")
//    UUID invitationUuid;

}
