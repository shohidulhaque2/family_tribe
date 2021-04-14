package com.shohidulhaque.user_registry_service.data_transfer_object;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.CreatePersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.dto_validation.EmailShouldNotExist;
import com.shohidulhaque.user_registry_service.dto_validation.EmailShouldNotExistGroup;
import com.shohidulhaque.user_registry_service.dto_validation.NotWeakPassword;
import com.shohidulhaque.user_registry_service.dto_validation.NotWeakPasswordGroup;
import com.shohidulhaque.user_registry_service.dto_validation.PasswordMatches;
import com.shohidulhaque.user_registry_service.dto_validation.PasswordMatchesGroup;
import com.shohidulhaque.user_registry_service.dto_validation.PasswordSize;
import com.shohidulhaque.user_registry_service.dto_validation.SensitiveData;
import com.shohidulhaque.user_registry_service.dto_validation.UsernameShouldNotExist;
import com.shohidulhaque.user_registry_service.dto_validation.UsernameShouldNotExistGroup;
import com.shohidulhaque.user_registry_service.message_keys.ValidationMessageKeys;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuperBuilder
@AllArgsConstructor
@ToString
@PasswordMatches(groups = {PasswordMatchesGroup.class})
@NotWeakPassword(groups = {NotWeakPasswordGroup.class})
public class ValidatedPersonRegistrationDtoRequest extends CreatePersonRegistrationDtoRequest {

    static Logger Logger = LoggerFactory.getLogger(ValidatedPersonRegistrationDtoRequest.class);
    static final String EMPTY_PASSWORD_VALUE = "";
    @NotBlank
    @Length(max = 255)
    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @NotBlank
    @Length(max = 255)
    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Length(max = 255)
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = ValidationMessageKeys.UsernameNeedsToHaveValidNameKey)
    @UsernameShouldNotExist(groups = {UsernameShouldNotExistGroup.class})
    @Override
    public String getNickname() {
        return this.nickname;
    }

    @SensitiveData
    @NotBlank
    @Length(min = PasswordSize.MINIMAL_PASSWORD_LENGTH, max = PasswordSize.MAX_PASSWORD_LENGTH)
//  @JsonDeserialize(using = SensitiveStringDeserializer.class)
    @JsonSerialize(using = SensitiveStringSerializer.class)
    @Override
    public String getPassword() {
        return this.password;
    }

    @SensitiveData
    @NotBlank
    @Length(min = PasswordSize.MINIMAL_PASSWORD_LENGTH, max = PasswordSize.MAX_PASSWORD_LENGTH)
    @Override
    public String getMatchingPassword() {
        return this.matchingPassword;
    }

    @EmailShouldNotExist(groups = {EmailShouldNotExistGroup.class})
    @Email
    @Length(min = 3, max = 255)
    @NotBlank
    @Override
    public String getEmail() {
        return this.email;
    }


}
