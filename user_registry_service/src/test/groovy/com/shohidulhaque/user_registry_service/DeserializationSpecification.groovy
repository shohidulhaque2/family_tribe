package com.shohidulhaque.user_registry_service

import spock.lang.Specification

class DeserializationSpecification extends Specification {

//    def 'derserializeErrorResponse'() {
//
//        given:
//        ObjectMapper jacksonObjectMapper = new ObjectMapper();
//        def json = "{\"content\":{\"error\":[{\"objectName\":\"UserRegistrationResponseDTO\",\"list\":[{\"fieldName\":\"lastName\",\"rejectedValue\":\"\",\"errorMessage\":\"must not be blank\",\"fieldDisplayName\":\"Last Name\"},{\"fieldName\":\"password\",\"rejectedValue\":\"\",\"errorMessage\":\"must not be blank\",\"fieldDisplayName\":\"Password\"},{\"fieldName\":\"matchingPassword\",\"rejectedValue\":\"\",\"errorMessage\":\"length must be between 8 and 32\",\"fieldDisplayName\":\"Re-entered Password\"},{\"fieldName\":\"firstName\",\"rejectedValue\":\"\",\"errorMessage\":\"must not be blank\",\"fieldDisplayName\":\"First Name\"},{\"fieldName\":\"password\",\"rejectedValue\":\"\",\"errorMessage\":\"length must be between 8 and 32\",\"fieldDisplayName\":\"Password\"},{\"fieldName\":\"email\",\"rejectedValue\":\"\",\"errorMessage\":\"must not be blank\",\"fieldDisplayName\":\"Email\"},{\"fieldName\":\"email\",\"rejectedValue\":\"\",\"errorMessage\":\"length must be between 3 and 255\",\"fieldDisplayName\":\"Email\"},{\"fieldName\":\"matchingPassword\",\"rejectedValue\":\"\",\"errorMessage\":\"must not be blank\",\"fieldDisplayName\":\"Re-entered Password\"},{\"fieldName\":\"password\",\"rejectedValue\":\"\",\"errorMessage\":\"must not be blank\",\"fieldDisplayName\":\"Password\"},{\"fieldName\":\"nickname\",\"rejectedValue\":\"\",\"errorMessage\":\"must not be blank, and it can contain can only contain lower case, upper case, numbers and underscores only.\",\"fieldDisplayName\":\"Nickname\"}],\"type\":{\"message\":\"The registration information that is given is not valid. It does not match the kinds of data that should be given for a registration.\",\"code\":0,\"subCode\":0}}]},\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost:8081/api/v1/users/registration\"}]}"
//
//        when:
//        UserRegistrationResponseDTO userRegistrationResponseDTO = jacksonObjectMapper.readValue(json, UserRegistrationResponseDTO)
//
//        then:
//        userRegistrationResponseDTO.getContent().getError().size() == 1
//    }
}
