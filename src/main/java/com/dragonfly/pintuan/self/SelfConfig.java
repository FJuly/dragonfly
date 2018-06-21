package com.dragonfly.pintuan.self;

import com.dragonfly.pintuan.bo.PriceRangeBo;

import java.util.*;

public class SelfConfig {
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

    // 折扣参数
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
    static Map<String, Double> maxSpreadMap = new HashMap<>();

    static {
        maxSpreadMap.put("美容彩妆", 3000d);
        maxSpreadMap.put("母婴", 2000d);
        maxSpreadMap.put("运动户外", 3000d);
        maxSpreadMap.put("个人洗护", 500d);
        maxSpreadMap.put("服装鞋靴", 2000d);
        maxSpreadMap.put("营养保健", 1000d);
        maxSpreadMap.put("家居生活", 3000d);
        maxSpreadMap.put("轻奢", 5000d);
        maxSpreadMap.put("环球美食", 1200d);
        maxSpreadMap.put("手表配饰", 3000d);
        maxSpreadMap.put("数码家电", 3000d);
        maxSpreadMap.put("汽车用品", 0d);
    }

    static List<PriceRangeBo> minSpreadList = new ArrayList<>();

    static {
        PriceRangeBo priceRangeBo = new PriceRangeBo.Builder().withDownPrice(30).withUpPrice(30).withMinReductionPrice(0).build();
        minSpreadList.add(priceRangeBo);
        PriceRangeBo priceRangeBo1 = new PriceRangeBo.Builder().withDownPrice(30).withUpPrice(100).withMinReductionPrice(5).build();
        minSpreadList.add(priceRangeBo1);
        PriceRangeBo priceRangeBo2 = new PriceRangeBo.Builder().withDownPrice(100).withUpPrice(200).withMinReductionPrice(10).build();
        minSpreadList.add(priceRangeBo2);
        PriceRangeBo priceRangeBo3 = new PriceRangeBo.Builder().withDownPrice(200).withUpPrice(300).withMinReductionPrice(10).build();
        minSpreadList.add(priceRangeBo3);
        PriceRangeBo priceRangeBo4 = new PriceRangeBo.Builder().withDownPrice(300).withUpPrice(400).withMinReductionPrice(10).build();
        minSpreadList.add(priceRangeBo4);
        PriceRangeBo priceRangeBo5 = new PriceRangeBo.Builder().withDownPrice(400).withUpPrice(1000).withMinReductionPrice(20).build();
        minSpreadList.add(priceRangeBo5);
        PriceRangeBo priceRangeBo6 = new PriceRangeBo.Builder().withDownPrice(1000).withUpPrice(2000).withMinReductionPrice(30).build();
        minSpreadList.add(priceRangeBo6);
        PriceRangeBo priceRangeBo7 = new PriceRangeBo.Builder().withDownPrice(2000).withUpPrice(3000).withMinReductionPrice(50).build();
        minSpreadList.add(priceRangeBo7);
        PriceRangeBo priceRangeBo8 = new PriceRangeBo.Builder().withDownPrice(3000).withUpPrice(3000).withMinReductionPrice(80).build();
        minSpreadList.add(priceRangeBo8);
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

    final static int failReasonIndex = 15;
    final static int resultPriceIndex = 14;

    final static double rule2Param = 0.2; //rule2 系数
    final static int startDay = 90;
    final static int endDay = 360;
}
