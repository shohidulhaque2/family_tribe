package com.shohidulhaque.user_registry_service.service.registration;

import com.okta.sdk.resource.ResourceException;
import com.okta.sdk.resource.user.User;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.ContentPersonRegistrationSuccessResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.get.RegisteredPerson;
import com.shohidulhaque.my_people.common_model.security_model.UserSecurityAccessPrivilage;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessCode.SuccessType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import com.shohidulhaque.user_registry_service.config.authorization_server.openid_connect_1.okta.OktaConstants;
import com.shohidulhaque.user_registry_service.config.authorization_server.openid_connect_1.okta.OktaGroupId;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedPersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedUpdatePersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.dto_validation.EmailShouldNotExistGroup;
import com.shohidulhaque.user_registry_service.dto_validation.NotWeakPasswordGroup;
import com.shohidulhaque.user_registry_service.dto_validation.PasswordMatchesGroup;
import com.shohidulhaque.user_registry_service.dto_validation.PersonRegistrationDtoArgumentViolationResponse;
import com.shohidulhaque.user_registry_service.dto_validation.UsernameShouldNotExistGroup;
import com.shohidulhaque.user_registry_service.mapper.PersonRegistrationMapper;
import com.shohidulhaque.user_registry_service.mapper.ValidatedPersonRegistrationDtoRequestMapper;
import com.shohidulhaque.user_registry_service.model.PersonRegistration;
import com.shohidulhaque.user_registry_service.model.PersonVerificationToken;
import com.shohidulhaque.user_registry_service.properties.AccountActivationProperties;
import com.shohidulhaque.user_registry_service.repository.PersonRegistrationRepository;
import com.shohidulhaque.user_registry_service.repository.PersonVerificationTokenRepository;
import com.shohidulhaque.user_registry_service.service.ExpirationTimeCalculator;
import com.shohidulhaque.user_registry_service.service.UuidGenerator;
import com.shohidulhaque.user_registry_service.service.exception.UserRegistryActivationException;
import com.shohidulhaque.user_registry_service.service.exception.UserRegistryActivationExiredException;
import com.shohidulhaque.user_registry_service.service.exception.UserRegistryRegistryActivationDoesNotExistException;
import com.shohidulhaque.user_registry_service.service.registration.listener.OpenIdConnect2Proxy;
import com.shohidulhaque.user_registry_service.service.registration.listener.event.OnRegistrationCompleteEvent;
import com.shohidulhaque.user_registry_service.service.user_invitation.MailSender;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import org.passay.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//==================================================================================================
//TODO:look okta rules for such thing as how long an activation exist, how long usernames and passwords and make them match.
@Service
@Transactional
public class RegistrationService {
    static Logger Logger = LoggerFactory.getLogger(RegistrationService.class);
    //==============================================================================================
    PersonRegistrationRepository personRegistrationRepository;
    PasswordValidator passwordValidator;
    UuidGenerator uuidGenerator;
    MailSender mailSender;
    ValidatedPersonRegistrationDtoRequestMapper validatedPersonRegistrationDtoRequestMapper;
    PersonRegistrationMapper personRegistrationMapper;
    ApplicationEventPublisher eventPublisher;
    ExpirationTimeCalculator expirationTimeCalculator;
    PersonVerificationTokenRepository personVerificationTokenRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    AccountActivationProperties accountActivationProperties;
    OpenIdConnect2Proxy openIdConnect2Proxy;
    /**
     * user validator to check the syntax and some sematics of the input is valid to the service
     * registration. it is viable to do it this because the validator does all the wor
     */
    Validator validator;
    //==============================================================================================
    @Autowired
    public RegistrationService(
                                PersonRegistrationRepository personRegistrationRepository,
                                ValidatedPersonRegistrationDtoRequestMapper validatedPersonRegistrationDtoRequestMapper,
                                PersonRegistrationMapper personRegistrationMapper,
                                PasswordValidator passwordValidator,
                                Validator validator,
                                UuidGenerator uuidGenerator,
                                MailSender mailSender,
                                ApplicationEventPublisher eventPublisher,
                                ExpirationTimeCalculator expirationTimeCalculator,
                                PersonVerificationTokenRepository personVerificationTokenRepository,
                                BCryptPasswordEncoder bCryptPasswordEncoder,
                                AccountActivationProperties accountActivationProperties,
                                OpenIdConnect2Proxy openIdConnect2Proxy) {
        this.passwordValidator = passwordValidator;
        this.validator = validator;
        this.uuidGenerator = uuidGenerator;
        this.mailSender = mailSender;
        this.validatedPersonRegistrationDtoRequestMapper = validatedPersonRegistrationDtoRequestMapper;
        this.eventPublisher = eventPublisher;
        this.expirationTimeCalculator = expirationTimeCalculator;
        this.personVerificationTokenRepository = personVerificationTokenRepository;
        this.personRegistrationRepository = personRegistrationRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountActivationProperties = accountActivationProperties;
        this.openIdConnect2Proxy = openIdConnect2Proxy;
        this.personRegistrationMapper = personRegistrationMapper;
    }
    //==============================================================================================
    public boolean activateAccount(String verificationToken) throws UserRegistryActivationException
    {
        this.personVerificationTokenRepository
            .findVerificationToken(verificationToken)
            .ifPresentOrElse(
                (userVerificationToken) -> {
                    this.isVerificationTokenValid(userVerificationToken);
                    PersonRegistration newPersonRegistration = userVerificationToken.getPersonRegistration();
                    try {
                        final User user = this.openIdConnect2Proxy.getUser(newPersonRegistration);
                        final boolean DO_NOT_SEND_EMAIL = false;
                        user.activate(DO_NOT_SEND_EMAIL);
                        this.personRegistrationRepository.save(newPersonRegistration);
                        Logger.info("user " + newPersonRegistration.getIdentityServerUserId() + " has had their account activated on the identity server.");
                    } catch (ResourceException resourceException) {
                        String message = "an error occurred while getting and/or activating user on the identity server with identity server user id " + newPersonRegistration.getIdentityServerUserId();
                        Logger.warn(message);
                        final ResponseType errorResponseType = new ResponseType(
                            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Undefined.undefined.subCode);
                        throw new UserRegistryActivationException(message, errorResponseType,  ErrorType.registration);
                    }
                },
                () -> {
                    final ResponseType errorResponseType = new ResponseType(
                        HttpStatus.NOT_FOUND.toString(),
                        HttpStatus.NOT_FOUND.value(),
                        Undefined.undefined.subCode);
                    String message = "verification: " + verificationToken + " does not exist. no account has been activated.";
                    throw new UserRegistryRegistryActivationDoesNotExistException(message, errorResponseType, ErrorType.registration);
                }
            );
        return true;
    }
    private void isVerificationTokenValid(PersonVerificationToken personVerificationToken) throws UserRegistryActivationExiredException
    {
        if (this.expirationTimeCalculator.hasTimeExpired(personVerificationToken.getExpirationTime())) {
            String message = "the verification token " + personVerificationToken.getToken()
                + " has expired. it was valid until " + personVerificationToken.getExpirationTime()
                + ".";
            final ResponseType errorResponseType = new ResponseType(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                Undefined.undefined.subCode);
            throw new UserRegistryActivationExiredException(message, errorResponseType, ErrorType.registration);
        }
    }
    //==============================================================================================
    private UUID getUuidForNewUser()
    {
        return this.uuidGenerator.getUuid();
    }
    //==============================================================================================
    private String getVerificationToken(PersonRegistration personRegistration)
    {
        return this.uuidGenerator.getUuid()+ "-" + this.uuidGenerator.getUuid(personRegistration.getEmail());
    }
    //==============================================================================================
    public PersonRegistrationDtoResponse getPerson(UUID uuidOfPerson)
    {
        Optional<PersonRegistration> existingPersonRegistrationOptional = this.personRegistrationRepository.findPersonByUserUuid(uuidOfPerson);
        if(existingPersonRegistrationOptional.isEmpty()){
            ResponseType possibleErrorResponseType = new ResponseType(HttpStatus.NOT_FOUND.toString(),
                HttpStatus.NOT_FOUND.value(),
                Undefined.undefined.subCode);
            return PersonRegistrationDtoArgumentViolationResponse.GetResponseBodyForError(possibleErrorResponseType, ErrorType.registration);
        }
        PersonRegistration existingPersonRegistration = existingPersonRegistrationOptional.get();
        return personHasBeenRetrievedSuccessfully(existingPersonRegistration);
    }
    private PersonRegistrationDtoResponse personHasBeenRetrievedSuccessfully(PersonRegistration existingPersonRegistration )
    {
        RegisteredPerson registeredPerson = this.personRegistrationMapper.to(existingPersonRegistration);
        ContentPersonRegistrationSuccessResponse contentPersonRegistrationSuccessResponse =
            ContentPersonRegistrationSuccessResponse.builder().type(SuccessType.general).payload(registeredPerson).build();
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.OK.toString())
            .code(HttpStatus.OK.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        PersonRegistrationDtoResponse personRegistrationDtoResponse = PersonRegistrationDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentPersonRegistrationSuccessResponse));
        return personRegistrationDtoResponse;
    }
    //==============================================================================================
    //TODO: create default basic roles for the user.
    public PersonRegistrationDtoResponse registerPerson(ValidatedPersonRegistrationDtoRequest personRegistrationDtoRequest)
    {
        //Set<ConstraintViolation<PersonRegistrationDtoRequest>> violations = this.validator.validate(personRegistrationDtoRequest);
        Set<ConstraintViolation<PersonRegistrationDtoRequest>> usernameAlreadyExistViolations = this.validator
            .validate(personRegistrationDtoRequest, UsernameShouldNotExistGroup.class);
        if (!usernameAlreadyExistViolations.isEmpty()) {
            return PersonRegistrationDtoArgumentViolationResponse.BadArgumentException(
                personRegistrationDtoRequest,
                usernameAlreadyExistViolations,
                ErrorType.registrationCreateUserUserNameAlreadyExist
            );
        }

        Collection<ConstraintViolation<PersonRegistrationDtoRequest>> userEmailAlreadyExistViolations = this.validator
            .validate(personRegistrationDtoRequest, EmailShouldNotExistGroup.class);
        if (!userEmailAlreadyExistViolations.isEmpty()) {
            return PersonRegistrationDtoArgumentViolationResponse.BadArgumentException(
                personRegistrationDtoRequest,
                userEmailAlreadyExistViolations,
                ErrorType.userEmailAddressAlreadyExistError
            );
        }

        Collection<ConstraintViolation<PersonRegistrationDtoRequest>> weakPasswordViolations = this.validator
            .validate(personRegistrationDtoRequest, NotWeakPasswordGroup.class);
        if (!weakPasswordViolations.isEmpty()) {
            return PersonRegistrationDtoArgumentViolationResponse.BadArgumentException(
                personRegistrationDtoRequest,
                weakPasswordViolations,
                ErrorType.userPassordwordQualityIssueError
            );
        }

        Collection<ConstraintViolation<PersonRegistrationDtoRequest>> passwordMatchesViolations = this.validator
            .validate(personRegistrationDtoRequest, PasswordMatchesGroup.class);
        if (!passwordMatchesViolations.isEmpty()) {
            return PersonRegistrationDtoArgumentViolationResponse.BadArgumentException(
                personRegistrationDtoRequest,
                passwordMatchesViolations,
                ErrorType.passwordAndReTypedPasswordDoNotMatch
            );
        }

        PersonRegistration personRegistration = this.validatedPersonRegistrationDtoRequestMapper
            .to(personRegistrationDtoRequest);

        final PersonRegistration[] personRegistrationsContainer = new PersonRegistration[1];

        personRegistration.setUuid(this.getUuidForNewUser());
        //make call to okta

        //TODO: LOOK INTO THIS. HOW LONG DOES THE SALT HAVE TO BE
        //String salt = BCrypt.gensalt(10);
        //String encryptedPassword = BCrypt.hashpw(newUser.getPassword(), salt);
        //int lastIndexOfDollarSign = newUser.getPassword().lastIndexOf("$");
        //String salt = newUser.getPassword().substring(lastIndexOfDollarSign + 1, lastIndexOfDollarSign + 22);
        //String password = newUser.getPassword().substring(lastIndexOfDollarSign + 22, newUser.getPassword().length());
        Set<String> roleNames = Arrays.stream(UserSecurityAccessPrivilage.PERSON.getRoles())
                .map(role -> OktaGroupId.GroupNameToGroupId.get(role.getRoleName()))
                .collect(Collectors.toSet());
        try {
            final User user = this.openIdConnect2Proxy.registerUserWithOAuthServer(
                    personRegistrationDtoRequest,
                    personRegistrationDtoRequest.getPassword(),
                    roleNames,
                    personRegistration.getUuid());
            String idOfUserOAuth2Server = user.getId();
            Logger.debug("created user on identity server." + " user has id " + idOfUserOAuth2Server + " on identity server.");
            Logger.debug("user status is " + user.getStatus());

            Optional.of(user.getLinks().get(OktaConstants.ACTIVATE_LINK))
                    .filter(Objects::nonNull)
                    .map(x -> (x instanceof Map) ? (Map) x : Map.of())
                    .map(x -> x.get(OktaConstants.ACTIVE_LINK_HREF))
                    .ifPresentOrElse(x -> {
                        personRegistration.setIdentityServerUserId(idOfUserOAuth2Server);
                        PersonVerificationToken personVerificationToken = PersonVerificationToken.builder()
                                .token(this.getVerificationToken(personRegistration))
                                .userVerificationLinkUri((String) x)
                                .uuid(this.uuidGenerator.getUuid())
                                .expirationTime(expirationTimeCalculator.addToCurrentUTCTime(this.accountActivationProperties.getTime()))
                                .build();
                        personRegistration.addPersonVerificationToken(personVerificationToken);
                        personRegistrationsContainer[0] = this.personRegistrationRepository.save(personRegistration);
                        Logger.info("publishing an event to send a verification email to the a user to activate their account.");
                        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
                            personVerificationToken));
                    }, () -> {
                        Logger.warn("active link should have been returned back by the identity server when the user was created, but was not, or it is not in the expected format.");
                        //TODO: error. throw exception
                    });
        } catch (ResourceException resourceException) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage
                    .append("an error has occurred while sending call to openid connect server to create a user. ")
                    .append("the error is ")
                    .append(resourceException.getError().getMessage()).append(". ")
                    .append("the error code is ").append(resourceException.getError().getId()).append(". ")
                    .append("the error status is ").append(resourceException.getStatus());
            Logger.warn(errorMessage.toString(), resourceException);
            throw resourceException;
        }

        PersonRegistrationDtoResponse personRegistrationDtoResponse = personHasBeenCreatedSuccessfully(personRegistrationsContainer[0]);
        ResponseType responseType = ResponseType
            .builder()
            .code(HttpStatus.CREATED.value())
            .message(HttpStatus.CREATED.toString()).build();
        personRegistrationDtoResponse.getContent().setResponseType(responseType);
        return personRegistrationDtoResponse;
    }
    private PersonRegistrationDtoResponse personHasBeenCreatedSuccessfully(PersonRegistration personRegistration)
    {
        RegisteredPerson registeredPerson = this.personRegistrationMapper.to(personRegistration);
        ContentPersonRegistrationSuccessResponse contentPersonRegistrationSuccessResponse =
            ContentPersonRegistrationSuccessResponse.builder().type(SuccessType.general).payload(registeredPerson).build();
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.CREATED.toString())
            .code(HttpStatus.CREATED.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        PersonRegistrationDtoResponse personRegistrationDtoResponse = PersonRegistrationDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentPersonRegistrationSuccessResponse));
        return personRegistrationDtoResponse;
    }
    private static void CopyValueIfFieldIsNullInValidatedUpdatePersonRegistrationDtoRequest(
        PersonRegistration personRegistration,
        ValidatedUpdatePersonRegistrationDtoRequest validatedUpdatePersonRegistrationDtoRequest)
    {
        if(Objects.isNull(validatedUpdatePersonRegistrationDtoRequest.getFirstName())){
            validatedUpdatePersonRegistrationDtoRequest.setFirstName(personRegistration.getFirstName());
        }
        if(Objects.isNull(validatedUpdatePersonRegistrationDtoRequest.getLastName())){
            validatedUpdatePersonRegistrationDtoRequest.setLastName(personRegistration.getLastName());
        }
    }
    //==============================================================================================
    public PersonRegistrationDtoResponse updatePerson(UUID uuidOfUser, ValidatedUpdatePersonRegistrationDtoRequest updatePersonRegistrationDtoRequest)
    {
        Optional<PersonRegistration> personRegistrationOptional = this.personRegistrationRepository.findPersonByUserUuid(uuidOfUser);
        if(personRegistrationOptional.isEmpty()){
            final ResponseType errorResponseType = new ResponseType(
                HttpStatus.BAD_REQUEST.toString(),
                HttpStatus.BAD_REQUEST.value(),
                Undefined.undefined.subCode);
            return PersonRegistrationDtoArgumentViolationResponse.GetResponseBodyForError(errorResponseType,ErrorType.registration);
        }

        PersonRegistration personRegistration = personRegistrationOptional.get();
        CopyValueIfFieldIsNullInValidatedUpdatePersonRegistrationDtoRequest(personRegistration, updatePersonRegistrationDtoRequest);

        Set<ConstraintViolation<PersonRegistrationDtoRequest>> validatedUpdatePersonRegistrationDtoRequestExistViolations = this.validator
            .validate(updatePersonRegistrationDtoRequest, Default.class);
        if (!validatedUpdatePersonRegistrationDtoRequestExistViolations.isEmpty()) {
            return PersonRegistrationDtoArgumentViolationResponse.BadArgumentException(
                updatePersonRegistrationDtoRequest,
                validatedUpdatePersonRegistrationDtoRequestExistViolations,
                ErrorType.registration);
        }
        this.personRegistrationMapper.to(updatePersonRegistrationDtoRequest, personRegistration);
        return personHasBeenUpdatedSuccessfully(personRegistration);
    }
    private PersonRegistrationDtoResponse personHasBeenUpdatedSuccessfully(PersonRegistration personRegistration)
    {
        RegisteredPerson registeredPerson = this.personRegistrationMapper.to(personRegistration);
        ContentPersonRegistrationSuccessResponse contentPersonRegistrationSuccessResponse =
            ContentPersonRegistrationSuccessResponse.builder().type(SuccessType.general).payload(registeredPerson).build();
        ResponseType responseType = ResponseType
            .builder()
            .message(HttpStatus.OK.toString())
            .code(HttpStatus.OK.value())
            .subCode(Undefined.undefined.getSubCode())
            .build();
        PersonRegistrationDtoResponse personRegistrationDtoResponse = PersonRegistrationDtoResponse.SuccessUserProfileDtoResponse(responseType, List.of(contentPersonRegistrationSuccessResponse));
        return personRegistrationDtoResponse;
    }
    //==============================================================================================
}
//==================================================================================================