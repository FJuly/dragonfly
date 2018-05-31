package com.dragonfly.grammer;

public class StaticInit {

    static String str = "init";

    static {
        System.out.println(str);
    }


    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.dragonfly.grammer.StaticInit");
    }
}
