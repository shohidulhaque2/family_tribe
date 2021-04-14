package com.shohidulhaque.my_people.common_model.data_transfer_object.base_model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@ToString
public class Content <R, S, E> implements Serializable {
    R responseType;
    List<S> success;
    List<E> error;
}
