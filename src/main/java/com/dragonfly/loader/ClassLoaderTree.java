package com.dragonfly.loader;

public class ClassLoaderTree {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        FileSystemClassLoader loader = new FileSystemClassLoader("/Users/fanggang/testbean");
//        Class<?> class1 = loader.loadClass("com.dragonfly.loader.TestBean");


        Class<?> class2 = loader.loadClass("java.lang.Object");

        System.out.println(class2.getClassLoader());


//        System.out.println(class1.getClassLoader());
//        IBean bean = (IBean) class1.newInstance();
//
//        bean.testBean();
//        Object object = new Object();
//
//        Object object = new Object();
//        System.out.println(object.getClass().getClassLoader());
//
//        System.out.println(Thread.currentThread().getContextClassLoader());
//
//        System.out.println(ClassLoader.getSystemClassLoader());


        // 打破双亲委派模型只要重写loadClass方法即可
    }
}
