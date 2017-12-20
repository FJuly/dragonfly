package com.dragonfly.generics;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Holder<T> {

    private T[] objects;

    private T item;

    public void put(T item) {
        objects = (T[])new Object[10]; // 这里的转型存在什么意义 转型是帮助在编译期间检查类型有效性
        this.item = item;
    }

    public T get() {
        return item;
    }


    public static <E extends TwoMethod> void testTwoMethod(E method) {
        method.testA();
        method.testB();
    }

    public void wildHolder(Holder<?> holder, Object object) {
         // holder.put(object);
    }

    public static void main(String[] args) {
//        Holder<Father> holder = new Holder<>();
//        holder.put(new Son());
//        Son son = (Son) holder.get();

        // Holder.testTwoMethod(new TwoMethodImpl());

        Holder<Father> fatherHolder = new Holder<>();
        fatherHolder.put(new Father());
        Father father = fatherHolder.get();

        // Collections.reverseOrder()

        Favorites favorites = new Favorites();

        Class stringClass = String.class;
        favorites.putFavorite(stringClass, 1);
    }
}

class Father {}

class Son extends Father{}


interface TwoMethod {

    void testA();

    void testB();
}

class TwoMethodImpl implements TwoMethod {

    @Override
    public void testA() {
        System.out.println("method A");
    }

    @Override
    public void testB() {
        System.out.println("method B");
    }
}

class ClassAsFactory<T> {

    private T x;

    public void create(int arg, Class<T> kind) {
        try {
            for (Constructor<?> ctor : kind.getConstructors()) {
                Class<?>[] params = ctor.getParameterTypes();
                if (params.length == 1) {
                    if (params[0] == int.class) {
                        x = kind.cast(ctor.newInstance(arg));
                    }
                }
            }
            x = kind.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

class GenericArrayWithTypeToken<T> {

    private Object[] array;
    public GenericArrayWithTypeToken(Class<T> kind,  int sz) {
        array = (T[])Array.newInstance(kind, sz);
    }

    public T[] rep() {
        return (T[])array;
    }
    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> gai =
                new GenericArrayWithTypeToken<>(Integer.class, 10);
        Integer[] ia = gai.rep();
    }
}


class Favorites{
    private Map<Class<?>, Object> favorites = new HashMap<>();

    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(type, instance);
    }
}



// class 创建数组

// 1. 泛型类与泛型方法没什么关系，更推荐使用泛型方法
// 2. 泛型类没法使用类型推断,泛型方法可以
// 3. 开放式泛型, 闭合式泛型
// 4. 擦除后没有类型信息，如何理解擦除，泛型只是提供了一些强制转换
// 5. 创建类型实例的几种方法
// 6. 类型捕获

