package com.dragonfly.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GenericAndCovariance {

    public static void main(String[] args) {
        List<Apple> list1 = Arrays.asList(new Apple());

        List<Orange> list2 = Arrays.asList(new Orange());

        printlnFruit(list1);

        printlnFruit(list2);

        // List<Apple> list = new ArrayList<>();
        // List<Number> list = new ArrayList<>();

        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Apple());
        fruitList.add(new Fruit());

        test(fruitList);


        // appleList.add(new Fruit());

        // List<Fruit> fruitList = new ArrayList<>();

        List<Apple> appleList = new ArrayList<>();

        Collections.copy(fruitList, appleList);
    }

    static void test(List<? super Apple> appleList) {
        appleList.add(new Apple());
    }

    // List<? super Apple> list里面存储的是Apple的某个未知的父类
    // List<? extends Apple> list 里面存放的都是Apple的某个未知的子类


    // 通配符是在泛型变量T上进行限制

    // 放置到方法中更容易理解
    static void copy(List<? super Apple> dest, List<? extends Apple> src) {

    }


    static void printlnFruit(List<? extends Fruit> list) {

        List<Object> list1 = new ArrayList<>();
        List<Apple> list2 = new ArrayList<>();


        Collections.copy(list1, list2);
        System.out.println(list.get(0).eat());
    }
}

class Fruit {
    String eat() {
        return "eat";
    }
}

class Apple extends Fruit {
    @Override
    public String toString() {
        return "apple";
    }

    public void eatApple() {
        System.out.println("eat apple");
    }
}

class Orange extends Fruit {
    @Override
    public String toString() {
        return "orange";
    }
}
