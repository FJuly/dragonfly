package com.dragonfly;

public class Singleton {
    private static Singleton singleton = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton != null) {
            return singleton;
        }

        synchronized (Singleton.class) {
            if (singleton == null) {
                singleton = new Singleton();
            }
        }
        return singleton;
    }
}


// 写一个DCL
class DCLSingleton {

    private static volatile DCLSingleton singleton = null;

    private DCLSingleton() {
    }

    public static DCLSingleton getInstance() {
        if (singleton == null) {
            synchronized (DCLSingleton.class) {
                if (singleton == null) {
                    singleton = new DCLSingleton();
                }
            }
        }
        return singleton;
    }
}

enum Color {
    RED(1), GREEN(2), BLUE(3);

    private Object object;


    private int code;

    public Object getObject() {
        return object;
    }

    Color(int code) {
        this.code = code;
        object = new Object();
    }

    public int getCode() {
        return code;
    }

    public void test() {

    }
}

class Color1 {

    private int code;

    public static Color1 RED;

    public static Color1 BLUE;

    private Color1(int param) {
        code = param;
    }

    void test() {
        System.out.println(code);
    }

    static {
        RED = new Color1(1);
        BLUE = new Color1(2);
    }
}

// 静态内部类的写法
class SingleFactory {
    private static class InstanceHolder {
        public static final SingleFactory instance = new SingleFactory();
    }


    public static SingleFactory getInstance() {
        return InstanceHolder.instance;
    }

    private SingleFactory() {
    }
}
