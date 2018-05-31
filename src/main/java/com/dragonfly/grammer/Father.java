package com.dragonfly.grammer;

import java.util.ArrayList;
import java.util.List;

public class Father {
    public Father() {}

    public static void main(String[] args) {
        Father father = new Father();
        Son son = new Son();
        System.out.println(Father.class.isInstance(son));
        // System.out.println(son.getClass().isInstance(father));



        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        for(String item : list) {
            if ("1".equals(item)) {
                list.remove(item);
            }
        }
    }

}


 class Son extends Father{}