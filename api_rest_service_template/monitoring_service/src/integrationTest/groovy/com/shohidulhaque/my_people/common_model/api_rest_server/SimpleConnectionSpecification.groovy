package com.shohidulhaque.my_people.common_model.api_rest_server

import com.shohidulhaque.my_people.api_rest_server.data_model.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.ResponseEntity
import spock.lang.Specification

//https://objectpartners.com/2019/05/21/speed-up-spock-spring-tests/
//https://objectpartners.com/2017/04/18/spring-integration-testing-with-spock-mocks/
//https://howtodoinjava.com/spring-boot2/testing/testresttemplate-post-example/
//https://springbootdev.com/2018/05/18/spring-boot-test-and-spring-security-perform-http-basic-authentication-with-testresttemplate/
//https://objectpartners.com/2017/04/18/spring-integration-testing-with-spock-mocks/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleConnectionSpecification extends Specification {

    @Autowired
    TestRestTemplate testRestTemplate

    @LocalServerPort
    int randomLocalServerPort

//    @Value('${server.servlet.context-path}')
//    String contextPath

    //def setup() {}          // run before every feature method
    //def cleanup() {}        // run after every feature method
    //def setupSpec() {}     // run before the first feature method
    //def cleanupSpec() {}   // run after the last feature method

    def "simple basic auth" (){
        given:
        String user  = "admin"
        String password = "password"

        when:
        ResponseEntity<Message> response = testRestTemplate.withBasicAuth(user, password).getForEntity("/null", Message)

        then:
        response != null
        Message message = response.getBody()
        message.getMessage() != null
        message.getMessage() == "null end point"
    }


//    def "let me try thus #a and #b" () {
//        given:
//            String s = GroovyMock(String)
//            Observable o = Mock(Observable)
//        expect:
//            a == b
//        where:
//            a || b
//            1 || 1
//            2 || 2
//            3 || 3
//            4 || 4
//            5 || 5
//    }
//    def "simple test shohidul haque" (){
//        given:
//        Objects.isNull(null)
//        Class g = ClassLoader.systemClassLoader.loadClass("net.bytebuddy.asm.Advice")
//        System.out.println(g)
//
//        when:
//        Objects.isNull(null)
//
//        then:
//        1 == 1
//    }
//
//    def "maximum of two numbers"() {
//        expect:
//        Math.max(a, b) == c
//
//        where:
//        a << [3, 5, 9]
//        b << [7, 4, 9]
//        c << [7, 5, 9]
//    }
//
//    def "minimum of #a and #b is #c"() {
//        expect:
//        Math.min(a, b) == c
//
//        where:
//        a | b || c
//        3 | 7 || 3
//        5 | 4 || 4
//        9 | 9 || 9
//    }
//
//    def "#person.name is a #sex.toLowerCase() person"() {
//        expect:
//        person.getSex() == sex
//
//        where:
//        person                    || sex
//        new Person(name: "Fred")  || "Male"
//        new Person(name: "Wilma") || "Female"
//    }
//
//    static class Person {
//        String name
//        String getSex() {
//            name == "Fred" ? "Male" : "Female"
//        }
//    }
}