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
        discountMap.put("工厂店", 0.08);
        discountMap.put("美容彩妆", 0.1);
        discountMap.put("母婴", 0.14);
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

    // 最多降价y
    static Map<String, Double> maxSpreadYMap = new HashMap<>();

    static {
        maxSpreadYMap.put("工厂店", 200d);
        maxSpreadYMap.put("美容彩妆", 300d);
        maxSpreadYMap.put("母婴", 280d);
        maxSpreadYMap.put("运动户外", 360d);
        maxSpreadYMap.put("个人洗护", 70d);
        maxSpreadYMap.put("服装鞋靴", 300d);
        maxSpreadYMap.put("营养保健", 200d);
        maxSpreadYMap.put("家居生活", 450d);
        maxSpreadYMap.put("轻奢", 750d);
        maxSpreadYMap.put("环球美食", 180d);
        maxSpreadYMap.put("手表配饰", 300d);
        maxSpreadYMap.put("数码家电", 150d);
        maxSpreadYMap.put("汽车用品", Double.MAX_VALUE);
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
        PriceRangeBo priceRangeBo8 = new PriceRangeBo.Builder().withDownPrice(3000).withUpPrice(Double.MAX_VALUE).withMinReductionPrice(80).build();
        minSpreadList.add(priceRangeBo8);
    }


    // 排除的品牌名
    static List<String> excludeBrandList = new ArrayList<>();

    static {
        excludeBrandList.add("美迪惠尔");
        excludeBrandList.add("jayjun");
        excludeBrandList.add("JM");
        excludeBrandList.add("爱敬");
        excludeBrandList.add("A.by BOM");
        excludeBrandList.add("春雨");
        excludeBrandList.add("丽得姿");
        excludeBrandList.add("snp");
        excludeBrandList.add("蒂佳婷");
        excludeBrandList.add("whoo");
        excludeBrandList.add("雪花秀");
        excludeBrandList.add("兰芝");
        excludeBrandList.add("城野");
        excludeBrandList.add("欣兰");
        excludeBrandList.add("spa");
        excludeBrandList.add("唯兰颂");
        excludeBrandList.add("23 years old");
        excludeBrandList.add("huxley");
        excludeBrandList.add("avajar");
        excludeBrandList.add("呼吸");
        excludeBrandList.add("Ohui");
        excludeBrandList.add("安耐晒");
        excludeBrandList.add("NARIS UP");
        excludeBrandList.add("RE:CIPE");
        excludeBrandList.add("Spa treatment");
        excludeBrandList.add("THREE");
        excludeBrandList.add("DR.WU");
        excludeBrandList.add("雅诗兰黛");
        excludeBrandList.add("娇韵诗");
        excludeBrandList.add("欣兰");
        excludeBrandList.add("Eau Precieuse");
        excludeBrandList.add("EltaMD");
        excludeBrandList.add("FILORGA");
        excludeBrandList.add("FOREO LUNA™");
        excludeBrandList.add("茱莉寇");
        excludeBrandList.add("huxley");


        excludeBrandList.add("schiff");
        excludeBrandList.add("澳佳宝");
        excludeBrandList.add("Fatblaster");
        excludeBrandList.add("正官庄");
        excludeBrandList.add("Swisse");
        excludeBrandList.add("康维他");


        excludeBrandList.add("芳新");
        excludeBrandList.add("Umbra");
        excludeBrandList.add("拜耳");
        excludeBrandList.add("Joseph Joseph");
        excludeBrandList.add("乐美雅");
        excludeBrandList.add("Nittaya");
        excludeBrandList.add("柳宗理");


        excludeBrandList.add("Absolute Organic");
        excludeBrandList.add("茅台");
        excludeBrandList.add("五粮液");
        excludeBrandList.add("欣善怡");


        excludeBrandList.add("乐高");
        excludeBrandList.add("B.TOYS");
        excludeBrandList.add("hape");
        excludeBrandList.add("swimava");
        excludeBrandList.add("费雪");
        excludeBrandList.add("爱乐维");
        excludeBrandList.add("美德乐");
        excludeBrandList.add("佰奥朗德");
        excludeBrandList.add("澳佳宝");
        excludeBrandList.add("惠氏");
        excludeBrandList.add("阿迪达斯");
        excludeBrandList.add("耐克");
        excludeBrandList.add("梅丽莎");
        excludeBrandList.add("old shoes");
        excludeBrandList.add("iplay");
        excludeBrandList.add("康萃乐");
        excludeBrandList.add("嘉宝");
        excludeBrandList.add("life space");
        excludeBrandList.add("小红脸");
        excludeBrandList.add("cybex");
        excludeBrandList.add("abner");
        excludeBrandList.add("帕洛");
        excludeBrandList.add("可么多么");
        excludeBrandList.add("贝亲");
        excludeBrandList.add("ovelle");
        excludeBrandList.add("elave");


        excludeBrandList.add("袋鼠");
        excludeBrandList.add("娇韵诗");
        excludeBrandList.add("茱莉寇");
        excludeBrandList.add("利尻昆布");
        excludeBrandList.add("regenerate");
        excludeBrandList.add("丰添");
        excludeBrandList.add("tangle angle");
        excludeBrandList.add("潘婷");
        excludeBrandList.add("tenga");
        excludeBrandList.add("小林制药暖贴");
        excludeBrandList.add("botanist");
        excludeBrandList.add("amino");
        excludeBrandList.add("三橡树");
        excludeBrandList.add("grin");
        excludeBrandList.add("BabyFoot");
        excludeBrandList.add("Lavilin");
        excludeBrandList.add("Grow Gorgeous");
        excludeBrandList.add("Foltene");
        excludeBrandList.add("佳口林");
        excludeBrandList.add("FicceCode");
        excludeBrandList.add("皓乐齿");
        excludeBrandList.add("SOMANG");
        excludeBrandList.add("SHISEIDO");
        excludeBrandList.add("Summer's eve");
        excludeBrandList.add("欧仕派");
    }

    // 排除的品牌id
    static List<Integer> excludeBrandIdList = new ArrayList<>();

    static {

    }

    // 排除的二级类目
    static List<String> secondCateList = new ArrayList<>();

    static {
        secondCateList.add("奶粉");
        secondCateList.add("纸尿裤/拉拉裤");
        secondCateList.add("家用电器");
        secondCateList.add("3C数码");
        secondCateList.add("建材家装");
        secondCateList.add("宠物生活");
    }

    // 排除的三级类目
    static List<String> thirdCateList = new ArrayList<>();

    static {
        thirdCateList.add("学习设备");
    }


    final static double rule2Param = 0.2; //rule2 系数
    final static int startDay = 90;
    final static int endDay = 180;

    public final static String filePath = "/Users/fanggang/test-pintuan/test.xlsx";
    public final static int rowNums = 7;
    public final static int gradeIndex = 8;
    public final static int costIndex = 10;
    public final static int pagePriceIndex = 12;
    public final static int minPriceIndex = 13;
    public final static int maxPriceIndex = 14;
    public final static int skuIdIndex = 0;
    public final static int frontStoreIndex = 9;
    public final static int salesIndex = 11;
    public final static int cmtPriceIndex = 14;
    public final static int firstCategoryIndex = 2;
    public final static int secondCategoryIndex = 3;
    public final static int thirdCategoryIndex = 4;
    public final static int brandIndex = 6;
    public final static int brandIdIndex = 7;
    public final static int failReasonIndex = 5;
    public final static int resultPriceIndex = 1;
}
