package com.shohidulhaque.my_people.common_model.sucess_and_error_codes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public final class Undefined {
    public static final Undefined undefined = new Undefined();
    public final int code = 0;
    public final int subCode = 0;
    public final String message = "Undefined.";
    private Undefined(){
    }
}
