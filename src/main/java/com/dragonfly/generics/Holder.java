package com.dragonfly.generics;

public class Holder<T> {

    private T item;

    public void put(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }

    public static void main(String[] args) {
        Holder<Father> holder = new Holder<>();
        holder.put(new Son());
        Son son = (Son) holder.get();

    }
}

class Father {}

class Son extends Father{}


