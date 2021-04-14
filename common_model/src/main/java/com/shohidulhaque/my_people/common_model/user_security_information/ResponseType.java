package com.shohidulhaque.my_people.common_model.user_security_information;

import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResponseType {
    String message = "";
    Integer code = 0;
    Integer subCode = 0;
    //==============================================================================================
    public static ResponseType GetConflictResponseType() {
        return ResponseType
            .builder()
            .message(HttpStatus.CONFLICT.toString())
            .code(HttpStatus.CONFLICT.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
    }
    //==============================================================================================
    public static ResponseType GetBadRequestResponseType() {
        return ResponseType
            .builder()
            .message(HttpStatus.BAD_REQUEST.toString())
            .code(HttpStatus.BAD_REQUEST.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
    }
    //==============================================================================================
    public static ResponseType GetOkResponseType() {
        return ResponseType
            .builder()
            .message(HttpStatus.OK.toString())
            .code(HttpStatus.OK.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
    }
    //==============================================================================================
    public static ResponseType GetCreatedResponseType() {
        return ResponseType
            .builder()
            .message(HttpStatus.CREATED.toString())
            .code(HttpStatus.CREATED.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
    }
    //==============================================================================================
    public static ResponseType GetInternalServerResponseType() {
        ResponseType responseType = new ResponseType(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Undefined.undefined.subCode);
        return responseType;
    }
    //==============================================================================================
}
