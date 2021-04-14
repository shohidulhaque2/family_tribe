package com.shohidulhaque.my_people.config_cloud_service

import spock.lang.Ignore
import spock.lang.Specification

class Algorithm {

    List swap(int a, int b){
        int c = a
        a = b
        b = c
        [a,b]
    }

    void swap(int []num, int i, int j){
        int c = num[i]
        num[i] = num[j]
        num[j] = c
    }

    int[] bubbleSort(int []num){
        if(num.length == 0 || num.length == 1)
            num
        for(int i = 0; i < num.length; ++i){
            for(int j = 0; j < num.length - (1 + i); ++j){
                if(!(num[j] < num[j+1])){
                    swap(num, j, j+1)
                }
            }
        }
    }

//    List insertionSort(int [] num){
//        return []
//    }
//
//    List mergeSort(int [] num){
//        return []
//    }
//
//    List quickSort(int [] num){
//        return []
//    }


    def exponential(int base, int power){
        if(power == 0)
            return 1
        else return base * exponential(base, power - 1)
    }

}


class AlogirthmsSpecification extends Specification {

    Algorithm testObject

    def setupSpec() {

    }

    def setup() {
        testObject = new Algorithm()
    }

    @Ignore
    def 'simple_swap'() {
        given:"two numbers"
            int a = 1
            int b = 2
        when:"when these numbers are swapped"
            def result = testObject.swap(a,b)

        then:"the numbers should be swapped."
            result[0] == b
            result[1] == a
    }

    @Ignore
    def 'simple test'(){
        given:"the base is 2 and the power 4"
            int base = 2
            int power = 4
        when:"the exponential is calculated"
            int result = testObject.exponential(base, power)
        then:"the result should be 16"
            result == 16
    }


    def 'bubbleSort1'(){
        given:'an empty array'
            int [] array = []
        when:'the array is sorted'
            testObject.bubbleSort(array)
        then:'the array is empty'
            array.length == 0
    }


    def 'bubbleSort2'(){
        given:'an empty array'
        int [] array = [1]
        when:'the array is sorted'
        testObject.bubbleSort(array)
        then:'the array is empty'
        array.length == 1
        array[0] == 1
    }

    def 'bubbleSort3'(){
        given:'an empty array'
        int [] array = [2,1]
        when:'the array is sorted'
        testObject.bubbleSort(array)
        then:'the array is empty'
        array.length == 2
        array[0] == 1
        array[1] == 2
    }



    def 'bubbleSort4'(){
        given:'an empty array'
        int [] array = [56785, 5667, 2,785,565446, 12]
        when:'the array is sorted'
        testObject.bubbleSort(array)
        then:'the array is empty'
        array.length == 6
        array[0] == 2
        array[1] == 12
        array[2] == 785
        array[3] == 5667
        array[4] == 56785
        array[5] == 565446
    }

}