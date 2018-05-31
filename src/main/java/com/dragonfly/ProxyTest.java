package com.dragonfly;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProxyTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<FancyToy> toy = FancyToy.class;

        Method[] methods = toy.getMethods();
        Method method = methods[0];
        method.invoke(null);


        Field[] fields = toy.getFields();

        Field field = fields[0];
        FancyToy newToy = toy.newInstance();

    }
}


// 1 .class 不会获取class引用，不会出发类的初始化

// 2 Class.forName()会立即初始化类

// 3 Class<T> 确定是哪个类的class

// 4 isAssignableFrom

// 5 instanceof 你是这个类吗或者你是这个类的派生类吗， == 比较class对象没有考虑继承结构

// 6 反射到底干了什么事情


class FancyToy {

}
