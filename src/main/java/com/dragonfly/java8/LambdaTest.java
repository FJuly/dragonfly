package com.dragonfly.java8;

import java.util.Optional;

public class LambdaTest {

    public static void main(String[] args) {
        Optional<String> anotherName = Optional.of("Sana");
        Optional<String> shortName = anotherName.filter((value) -> value.length() < 6);
        System.out.println(shortName.get());
    }
}

class OuterClass {

    public int item;

     class StaticNestedClass {
        public void print() {
            // System.out.println;
        }
    }
}
