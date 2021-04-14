package com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ErrorResponse extends ErrorResponseBase {
  private  String objectName;
  private  List<FieldError> list;
  private  ErrorCode.ErrorType type;
}

