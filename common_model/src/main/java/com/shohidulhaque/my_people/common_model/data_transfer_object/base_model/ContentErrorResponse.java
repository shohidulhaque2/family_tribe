package com.shohidulhaque.my_people.common_model.data_transfer_object.base_model;

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
public class ContentErrorResponse  <E, P> {
    E responseType;
    P payload;
}
