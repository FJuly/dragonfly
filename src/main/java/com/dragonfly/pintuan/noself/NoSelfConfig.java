package com.dragonfly.pintuan.noself;

import com.dragonfly.pintuan.bo.PriceRangeBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoSelfConfig {

    static final double discount = 0.15;

    static List<PriceRangeBo> priceRangeBoList = new ArrayList<>();

    static {
        PriceRangeBo priceRangeBo = new PriceRangeBo.Builder().withDownPrice(50).withUpPrice(50).withMinReductionPrice(0).withMaxReductionPrice(0).build();
        priceRangeBoList.add(priceRangeBo);
        PriceRangeBo priceRangeBo1 = new PriceRangeBo.Builder().withDownPrice(50).withUpPrice(150).withMinReductionPrice(5).withMaxReductionPrice(20).build();
        priceRangeBoList.add(priceRangeBo1);
        PriceRangeBo priceRangeBo2 = new PriceRangeBo.Builder().withDownPrice(50).withUpPrice(400).withMinReductionPrice(10).withMaxReductionPrice(65).build();
        priceRangeBoList.add(priceRangeBo2);
        PriceRangeBo priceRangeBo3 = new PriceRangeBo.Builder().withDownPrice(400).withUpPrice(1000).withMinReductionPrice(20).withMaxReductionPrice(80).build();
        priceRangeBoList.add(priceRangeBo3);
        PriceRangeBo priceRangeBo4 = new PriceRangeBo.Builder().withDownPrice(1000).withUpPrice(3000).withMinReductionPrice(50).withMaxReductionPrice(200).build();
        priceRangeBoList.add(priceRangeBo4);
        PriceRangeBo priceRangeBo5 = new PriceRangeBo.Builder().withDownPrice(3000).withUpPrice(Double.MAX_VALUE).withMinReductionPrice(80).withMaxReductionPrice(400).build();
        priceRangeBoList.add(priceRangeBo5);
    }

    static Map<String, Double> noSelfRationMap = new HashMap<>();

    static {
        noSelfRationMap.put("A", 0.8);
        noSelfRationMap.put("A-", 0.8);
        noSelfRationMap.put("B", 0.8);
        noSelfRationMap.put("B-", 0.8);
        noSelfRationMap.put("C", 1.0);
        noSelfRationMap.put("D", 1.0);
        noSelfRationMap.put("E", 1.0);
        noSelfRationMap.put("新品", 1.0);
    }
}
