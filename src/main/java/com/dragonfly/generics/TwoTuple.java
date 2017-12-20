package com.dragonfly.generics;



import java.util.ArrayList;
import java.util.List;

public class TwoTuple<A,B> {

    public A item1;

    public B item2;

    TwoTuple(A a, B b) {
        this.item1 = a;
        this.item2 = b;
    }
}


class Tuple {
    public static <A,B> TwoTuple<A, B> tuple(A a, B b) {
        return new TwoTuple<>(a, b);
    }

}

class testTuple {
    public static void main(String[] args) {

        TwoTuple twoTuple2 = Tuple.tuple("23", 12);
        TwoTuple<Integer, Integer> tuple3 = (TwoTuple<Integer, Integer>) twoTuple2;

        System.out.print(tuple3.item1);

        TwoTuple<String, Integer> tuple4 = new TwoTuple<>("123", 1);



        List<Integer> list = new ArrayList<>();

        // List<String> list2 = (List<String>) list;
    }
}



