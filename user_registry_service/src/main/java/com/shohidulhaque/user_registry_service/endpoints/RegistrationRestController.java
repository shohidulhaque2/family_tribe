package com.shohidulhaque.user_registry_service.endpoints;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonPasswordResetDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoResponse;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.Undefined;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode.ErrorType;
import com.shohidulhaque.my_people.common_model.user_security_information.ResponseType;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedPersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.data_transfer_object.ValidatedUpdatePersonRegistrationDtoRequest;
import com.shohidulhaque.user_registry_service.dto_validation.PersonRegistrationDtoArgumentViolationResponse;
import com.shohidulhaque.user_registry_service.mapper.PersonRegistrationMapper;
import com.shohidulhaque.user_registry_service.mapper.ValidatedPersonRegistrationDtoRequestMapper;
import com.shohidulhaque.user_registry_service.service.registration.RegistrationService;
import java.util.UUID;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-registry-service")
public class RegistrationRestController {
    //==============================================================================================
    private final static Logger Logger = LoggerFactory.getLogger(RegistrationRestController.class);
    //==============================================================================================
    RegistrationService registrationService;
    //==============================================================================================
    Validator validator;
    //==============================================================================================
    PersonRegistrationMapper personRegistrationMapper;
    //==============================================================================================
    ValidatedPersonRegistrationDtoRequestMapper validatedPersonRegistrationDtoRequestMapper;
    //==============================================================================================
    @Autowired
    public RegistrationRestController(
        RegistrationService registrationService,
        Validator validator,
        ValidatedPersonRegistrationDtoRequestMapper validatedPersonRegistrationDtoRequestMapper,
        PersonRegistrationMapper personRegistrationMapper) {
        this.registrationService = registrationService;
        this.validator = validator;
        this.validatedPersonRegistrationDtoRequestMapper = validatedPersonRegistrationDtoRequestMapper;
        this.personRegistrationMapper = personRegistrationMapper;
    }
    //==============================================================================================
    //general exception handlers
    @ExceptionHandler({Exception.class})
    public ResponseEntity<PersonRegistrationDtoResponse> handleException(Exception e) {
        Logger.warn("exception handled by general error handling code.", e);
        ResponseType errorResponseType = new ResponseType(
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Undefined.undefined.subCode);
        PersonRegistrationDtoResponse personRegistrationDtoResponse = PersonRegistrationDtoArgumentViolationResponse.GetResponseBodyForError(errorResponseType, ErrorType.registration);
        return ResponseEntity.status(personRegistrationDtoResponse.getContent().getResponseType().getCode()).body(personRegistrationDtoResponse);
    }
    //==============================================================================================
    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<PersonRegistrationDtoResponse> handleAllDataAccessException(
        DataAccessException e) {
        Logger.warn("data access exception", e);
        ResponseType errorResponseType = new ResponseType(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Undefined.undefined.subCode);
        PersonRegistrationDtoResponse personRegistrationDtoResponse = PersonRegistrationDtoArgumentViolationResponse.GetResponseBodyForError(errorResponseType, ErrorType.registration);
        return ResponseEntity.status(personRegistrationDtoResponse.getContent().getResponseType().getCode()).body(personRegistrationDtoResponse);
    }
    //==============================================================================================
    @Autowired
    ApplicationContext context;
    //==============================================================================================
    @PreAuthorize("isAnonymous()")
    @PostMapping("/person")
    public ResponseEntity<PersonRegistrationDtoResponse> resetPerson(
            @RequestBody ValidatedPersonRegistrationDtoRequest personRegistrationDtoRequest) {
        PersonRegistrationDtoResponse  personRegistrationDtoResponse = this.registrationService.registerPerson(personRegistrationDtoRequest);
        personRegistrationDtoResponse.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                    .methodOn(RegistrationRestController.class)
                    .resetPerson(personRegistrationDtoRequest))
                .withSelfRel());
        return ResponseEntity
            .status(personRegistrationDtoResponse.getContent().getResponseType().getCode())
            .body(personRegistrationDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAnonymous()")
    @PostMapping("/person/reset")
    public ResponseEntity<PersonRegistrationDtoResponse> resetPerson(
        @RequestBody PersonPasswordResetDtoRequest personPasswordResetDtoRequest) {
        return ResponseEntity.ok().build();
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/person/{uuidOfPerson}")
    public ResponseEntity<PersonRegistrationDtoResponse> getRegisteredPerson(@PathVariable("uuidOfPerson") UUID uuidOfUser) {
        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.registrationService.getPerson(uuidOfUser);
        personRegistrationDtoResponse.add(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(RegistrationRestController.class)
                .getRegisteredPerson(uuidOfUser))
            .withSelfRel());
        return ResponseEntity
            .status(personRegistrationDtoResponse.getContent().getResponseType().getCode())
            .body(personRegistrationDtoResponse);
    }
    //==============================================================================================
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/person/{uuidOfPerson}")
    public ResponseEntity<PersonRegistrationDtoResponse> updateRegisteredPerson(@PathVariable("uuidOfPerson") UUID uuidOfUser,
        @RequestBody ValidatedUpdatePersonRegistrationDtoRequest updatePersonRegistrationDtoRequest) {
        PersonRegistrationDtoResponse personRegistrationDtoResponse = this.registrationService.updatePerson(uuidOfUser, updatePersonRegistrationDtoRequest);
            personRegistrationDtoResponse.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                    .methodOn(RegistrationRestController.class)
                    .updateRegisteredPerson(uuidOfUser, updatePersonRegistrationDtoRequest))
                .withSelfRel());
            return ResponseEntity
                .status(personRegistrationDtoResponse.getContent().getResponseType().getCode())
                .body(personRegistrationDtoResponse);
    }
    //==============================================================================================
}
