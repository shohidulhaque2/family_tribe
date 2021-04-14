package com.shohidulhaque.my_people.common_model.data_transfer_object.base_model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@ToString
public class DtoResponse <C>  extends RepresentationModel<DtoResponse<C>> implements Serializable {
    C content;
}
