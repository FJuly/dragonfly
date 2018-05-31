package com.dragonfly.grammer;


import java.util.HashSet;

public class CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        CloneSon cloneSon = new CloneSon(2, 3);

        // CloneSon newCloneSon = cloneSon.clone();

        // System.out.println(newCloneSon.getItem());

        CloneFather cloneFather = new CloneFather(2);


        System.out.println(cloneFather.d);

        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("123");
        hashSet.add("123");
        System.out.println(hashSet);
    }
}


class CloneFather implements Cloneable {
    private int a;


    protected int c;

    int d;

    public CloneFather(int item) {
        a = item;
        testOverride();
    }

    public int getItem() {
        return a;
    }

    @Override
    public CloneFather clone() throws CloneNotSupportedException {
        return (CloneFather) super.clone();
    }

    public void testOverride() {
        System.out.println(" i am father!");
    }
}




class CloneSon extends CloneFather {

    private int b;

    public CloneSon(int a, int b) {
        super(a);
        this.b = b;
    }

    @Override
    public CloneSon clone() throws CloneNotSupportedException {
        return (CloneSon) super.clone(); // 他妈的简直神奇
    }

    @Override
    public void testOverride() {
        System.out.println(" i am son!");
    }
}
