package com.shohidulhaque.user_registry_service.data_transfer_object;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorResponse;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessResponse;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class UserRegistrationResponse {

  private List<SuccessResponse> success;
  private List<ErrorResponse> error;

}
