package com.dragonfly.grammer;

public class Father {
    public Father() {}

    public static void main(String[] args) {
        Father father = new Father();
        Son son = new Son();
        System.out.println(Father.class.isInstance(son));
        // System.out.println(son.getClass().isInstance(father));
    }
}


 class Son extends Father{}