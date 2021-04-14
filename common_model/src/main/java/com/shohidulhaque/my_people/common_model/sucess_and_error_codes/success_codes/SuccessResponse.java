package com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {

  private SuccessCode.SuccessType type;
}
