package com.dragonfly.loader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTree {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {


        ClassLoader myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name)
                    throws ClassNotFoundException {
                try {
                    String filename = name.substring(name.lastIndexOf(".") + 1)
                            + ".class";
                    InputStream is = getClass().getResourceAsStream(filename);
                    if (is == null) {
                        return super.loadClass(name);   // 递归调用父类加载器
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Class<?> class1 = myClassLoader.loadClass("com.dragonfly.loader.MyTestBean");
        Object object = class1.newInstance();

        System.out.println(object instanceof MyTestBean);
//
//        Method testBeanMethod = class1.getMethod("test", null);
//        testBeanMethod.invoke(object, null);
//        System.out.println(object.getClass().getClassLoader());


//        FileSystemClassLoader loader1 = new FileSystemClassLoader("/Users/fanggang/testbean");
//        FileSystemClassLoader loader2 = new FileSystemClassLoader("/Users/fanggang/testbean");

//        Class<?> class1 = loader.loadClass("com.dragonfly.loader.TestBean");


//        loader1.getParent().loadClass("com.dragonfly.loader.IBean");
//
//        Class<?> class1 = loader1.loadClass("com.dragonfly.loader.TestBean");
//        Class<?> class2 = loader2.loadClass("com.dragonfly.loader.TestBean");


//        Object object1 = class1.newInstance();
//        Object object2 = class2.newInstance();

        // System.out.println("IBean:" + IBean.class.getClassLoader());
//        System.out.println("object1:" + object1.getClass().getClassLoader());
//        System.out.println("object2:" + object2.getClass().getClassLoader());
//
//        IBean iBean = (IBean) object1;
        // SmallBean smallBean = iBean.getSmallBean();
//        iBean.testBean();
        // System.out.println(smallBean.getClass().getClassLoader());

//        AppTestBean appTestBean = new AppTestBean();
//        IBean appIBean = (IBean)appTestBean;
//        IBean iBean = (IBean) object1;
//        IBean iBean2 = (IBean) object2;
//        appIBean.testBean();
//        iBean.testBean();
//        iBean2.testBean();
        // System.out.println(object1 instanceof IBean);

//        System.out.println(class1 == class2);
//        System.out.println(class1.getClassLoader());
//        System.out.println(class2.getClassLoader());


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
