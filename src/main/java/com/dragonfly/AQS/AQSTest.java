package com.dragonfly.AQS;

import java.util.concurrent.atomic.AtomicInteger;

public class AQSTest {
    public static void main(String[] args) {


        try {
            m1();
            m2();

        } catch (RuntimeException e) {
            System.out.println("exception");
        }
        m2();





//        AtomicInteger atomicInteger = new AtomicInteger();
//        atomicInteger.addAndGet(7);
//
//        ThreadLocal<String> local1 = new ThreadLocal<>();
//        local1.set("1111");
//
//
//
//        ThreadLocal<String> local2 = new ThreadLocal<>();
//        local2.set("22222");
//
//
//
//
//        System.out.println(local1.get());
//
//        System.out.println(local2.get());

    }

    private static void m1()  {
        throw new RuntimeException("SSS");
    }

    private static void m2()  {
        System.out.println("m2");
    }
}
