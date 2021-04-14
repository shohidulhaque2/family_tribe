package com.shohidulhaque.chat_service.data_model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ChatSpaceUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(unique = true, updatable = false, nullable = false)
    UUID uuid;

    @org.hibernate.annotations.Type(type="uuid-char")
    @Column(unique = true, nullable = false)
    @NotNull
    UUID creatorUuid;

    @Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<ChatSpace> chatSpace = new LinkedList<>();

    @Version
    @Column(nullable = false)
    Long version;

    public void addChatSpace(ChatSpace chatSpace){
        this.chatSpace.add(chatSpace);
    }

}
