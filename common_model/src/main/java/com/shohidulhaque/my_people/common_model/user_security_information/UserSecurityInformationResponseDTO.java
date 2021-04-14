package com.shohidulhaque.my_people.common_model.user_security_information;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserSecurityInformationResponseDTO {

    ContentUserSecurityInformationResponse content;
}
