package com.dragonfly.loader;

public class AppTestBean implements IBean {
    @Override
    public void testBean() {
        System.out.println("我是app加载器哦");
    }

    @Override
    public SmallBean getSmallBean() {
        return null;
    }
}
