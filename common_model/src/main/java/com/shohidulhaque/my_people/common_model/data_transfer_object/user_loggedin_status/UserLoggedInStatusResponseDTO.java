package com.shohidulhaque.my_people.common_model.data_transfer_object.user_loggedin_status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoggedInStatusResponseDTO {

    boolean loggedIn;
}
