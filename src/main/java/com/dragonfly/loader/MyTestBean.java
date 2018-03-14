package com.dragonfly.loader;

public class MyTestBean implements IBean {

    public SmallBean smallBean = new SmallBean();

    @Override
    public void testBean() {
    }

    @Override
    public SmallBean getSmallBean() {
        return null;
    }

    public void test() {
        System.out.println(SmallBean.class.getClassLoader());
    }
}
