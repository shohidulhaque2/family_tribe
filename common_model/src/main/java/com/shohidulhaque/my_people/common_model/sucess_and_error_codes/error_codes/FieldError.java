package com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldError {

  private String fieldName;
  private String rejectedValue;
  private String errorMessage;
  //TODO: remove no longer needed
  private String fieldDisplayName;
}
