package com.dragonfly;

import java.util.HashMap;
import java.util.Map;

public class NIOTest {
    static public void main(String args[]) {
        int j = (32-1)&100;
        System.out.println(j);
        HashMap<String, String> testHashMap = new HashMap<>(3);
        testHashMap.put("test", "test");
        testHashMap.put("test1", "test");
        testHashMap.put("test2", "test");
        testHashMap.put("test3", "test");
        testHashMap.put("test4", "test");
        testHashMap.put("test5", "test");
        Map<String, String> cloneMap = (Map<String, String>)testHashMap.clone();

    }
}


