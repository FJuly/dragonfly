package com.dragonfly;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class collection {
    public static void main(String[] args) {
//        TreeMap<String, String> treeMap = new TreeMap<>();
//        treeMap.put(null, "222");

//        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
//        linkedHashMap.put(null, "111");

        BigDecimal b1 = new BigDecimal(1122333);
        BigDecimal b2 = new BigDecimal(100);
        String bookAmount = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toString();
        System.out.println(bookAmount);
    }
}
