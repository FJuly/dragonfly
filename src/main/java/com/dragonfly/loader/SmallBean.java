package com.dragonfly.loader;

public class SmallBean {

    public void print() {
        System.out.println("SmallBean Classloader:" + SmallBean.class.getClassLoader());
    }
}
