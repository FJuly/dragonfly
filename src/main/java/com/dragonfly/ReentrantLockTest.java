package com.dragonfly;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
//        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//
//        Thread thread1 = new Thread(() -> map.put("Alan", "1"));
//
//        Thread thread2 = new Thread(() -> System.out.println(map.get("Alan")));
//
//        thread1.start();
//
//        thread2.start();
//
//        thread1.join();
//        thread2.join();
//        A();
//
//
//        new Thread(() -> {
//            A();
//            System.out.println("other thread");
//        }).start();
//
//        while (true) {
//
//        }

        List<A> a = new ArrayList<A>() ;
        a.add(new A("1", 1)) ;
        a.add(new A("2", 2)) ;
        a.add(new A("3", 3)) ;
//直接返回对象就不会有反斜杠。。
        Object o = JSONObject.toJSON(a) ;
        System.out.println(o) ;
    }


    private static void A() {
        reentrantLock.lock();
        B();
        reentrantLock.unlock();

    }

    private static void B() {
        reentrantLock.lock();
        System.out.println("B");
        reentrantLock.unlock();
    }
}

class A{
    String name ;
    Integer age ;
    A(String name , Integer age){
        this.name = name ;
        this.age = age ;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

}