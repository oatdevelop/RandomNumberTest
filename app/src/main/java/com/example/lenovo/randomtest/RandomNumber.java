package com.example.lenovo.randomtest;

/**
 * Created by lenovo on 10/1/2017.
 */

public class RandomNumber {

        public String CompareRandomNumber(int myNumber, int randomNumber){
            if(myNumber < randomNumber){
                return "Your number is " + myNumber + " less than a random number";
            }else if(myNumber > randomNumber){
                return "Your number is " + myNumber + " greater than a random number";
            }else {
                return "Congratulations";
            }
        }

}
