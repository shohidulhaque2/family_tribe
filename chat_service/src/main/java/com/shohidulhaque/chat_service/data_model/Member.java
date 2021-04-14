package com.shohidulhaque.chat_service.data_model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"chatSpace"})
@ToString(exclude = {"chatSpace"})
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //add later
//    @GenericGenerator(
//        name = "UUID",
//        strategy = "org.hibernate.id.UUIDGenerator")
//    @org.hibernate.annotations.Type(type="uuid-char")

    @NotNull
    @Column(unique = true, updatable = false, nullable = false)
    UUID uuid;

    //TODO: REMOVE THIS
    @NotNull
    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(nullable = false)
    UUID memberUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    ChatSpace chatSpace;

}
