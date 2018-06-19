package com.dragonfly.pintuan;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmtConfig {
    // 降价系数
    static Map<String, Double> rationMap = new HashMap<>();
    static {
        rationMap.put("A", 0.4);
        rationMap.put("A-", 0.4);
        rationMap.put("新品", 0.2);
        rationMap.put("B", 0.4);
        rationMap.put("B-", 0.4);
        rationMap.put("C", 0.8);
        rationMap.put("D", 1.0);
        rationMap.put("E", 1.0);
    }

    // x参数
    static Map<String, Double> discountMap = new HashMap<>();
    static {
        discountMap.put("美容彩妆", 0.1);
        discountMap.put("母婴", 0.17);
        discountMap.put("运动户外", 0.12);
        discountMap.put("个人洗护", 0.14);
        discountMap.put("服装鞋靴", 0.15);
        discountMap.put("营养保健", 0.2);
        discountMap.put("家居生活", 0.15);
        discountMap.put("轻奢", 0.15);
        discountMap.put("环球美食", 0.15);
        discountMap.put("手表配饰", 0.1);
        discountMap.put("数码家电", 0.05);
        discountMap.put("汽车用品", 0.04);
    }

    // 最高降价
    static Map<String, Double> maxPinTuanPriceMap = new HashMap<>();
    static {
        maxPinTuanPriceMap.put("美容彩妆", 3000d);
        maxPinTuanPriceMap.put("母婴", 2000d);
        maxPinTuanPriceMap.put("运动户外", 3000d);
        maxPinTuanPriceMap.put("个人洗护", 500d);
        maxPinTuanPriceMap.put("服装鞋靴", 2000d);
        maxPinTuanPriceMap.put("营养保健", 1000d);
        maxPinTuanPriceMap.put("家居生活", 3000d);
        maxPinTuanPriceMap.put("轻奢", 5000d);
        maxPinTuanPriceMap.put("环球美食", 1200d);
        maxPinTuanPriceMap.put("手表配饰", 3000d);
        maxPinTuanPriceMap.put("数码家电", 3000d);
        maxPinTuanPriceMap.put("汽车用品", 0d);
    }

    // 最小降价
    static TreeMap<Double, Double> minSpreadMap = new TreeMap<>();
    static {
        minSpreadMap.put(-1d, 80d);
        minSpreadMap.put(30d, 0d);
        minSpreadMap.put(100d, 5d);
        minSpreadMap.put(200d, 10d);
        minSpreadMap.put(300d, 10d);
        minSpreadMap.put(400d, 10d);
        minSpreadMap.put(1000d, 20d);
        minSpreadMap.put(2000d, 30d);
        minSpreadMap.put(3000d, 50d);
    }

    final static int rowNums = 10800;
    final static String filePath = "/Users/fanggang/test-pintuan/AB_1.xlsx";

    final static int gradeIndex = 6;
    final static int costIndex = 8;
    final static int pagePriceIndex = 10;
    final static int minPriceIndex = 11;
    final static int maxPriceIndex = 12;
    final static int skuIdIndex = 0;
    final static int frontStoreIndex = 7;
    final static int salesIndex = 9;
    final static int cmtPriceIndex = 13;
    final static int firstCategoryIndex = 2;
    final static int brandIndex = 5;

    public final static int failReasonIndex = 15;
    public final static int resultPriceIndex = 14;

    final static double rule2Param = 0.2; // rule2 系数
    final static double constantY = 150; // 常数Y
    final static int startDay = 90;
    final static int endDay = 360;
}
