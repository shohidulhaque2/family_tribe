package com.shohidulhaque.my_people.common_model


import org.mockito.Mock
import spock.lang.Specification

class BasicTestSpecification extends Specification {


    def 'simpleTest2'() {
        given:
        TestObject testObject = new TestObject()
        TestObjectClient testObjectClient = Mock()
        testObject.setTestObjectClient(testObjectClient)
        testObjectClient.justAnArgument(_ as String)

        when:
        testObject.callMethodWithArgument("d")

        then:
        1 * testObjectClient.justAnArgument(_)
    }

    def 'dataDrivenTest'(int a, int b, int c) {
        expect:
            c == a + b
        where:
            a | b | c
            1 | 2 | 3
    }

}