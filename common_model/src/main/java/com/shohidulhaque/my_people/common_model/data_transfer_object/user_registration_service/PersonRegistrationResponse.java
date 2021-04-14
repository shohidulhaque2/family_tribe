package com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorResponse;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessResponse;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonRegistrationResponse {

  @Builder.Default
  protected  List<SuccessResponse> success = new LinkedList<>();

  @Builder.Default
  protected  List<ErrorResponse> error = new LinkedList<>();

}
