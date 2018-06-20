package com.dragonfly.pintuan.noself;

import com.dragonfly.pintuan.bo.Goods;
import com.dragonfly.pintuan.bo.PriceRangeBo;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoSelfComputeService {

    static final double discount = 0.15;

    static List<PriceRangeBo> priceRangeBoList = new ArrayList<>();

    static {
        PriceRangeBo priceRangeBo = new PriceRangeBo();
        priceRangeBo.setDownPrice(50);
        priceRangeBo.setUpPrice(50);
        priceRangeBo.setMinReductionPrice(0);
        priceRangeBo.setMaxReductionPrice(0);
        priceRangeBoList.add(priceRangeBo);
        PriceRangeBo priceRangeBo1 = new PriceRangeBo();
        priceRangeBo.setDownPrice(50);
        priceRangeBo.setUpPrice(150);
        priceRangeBo.setMinReductionPrice(5);
        priceRangeBo.setMaxReductionPrice(20);
        priceRangeBoList.add(priceRangeBo1);
        PriceRangeBo priceRangeBo2 = new PriceRangeBo();
        priceRangeBo.setDownPrice(50);
        priceRangeBo.setUpPrice(400);
        priceRangeBo.setMinReductionPrice(10);
        priceRangeBo.setMaxReductionPrice(65);
        priceRangeBoList.add(priceRangeBo2);
        PriceRangeBo priceRangeBo3 = new PriceRangeBo();
        priceRangeBo.setDownPrice(400);
        priceRangeBo.setUpPrice(1000);
        priceRangeBo.setMinReductionPrice(20);
        priceRangeBo.setMaxReductionPrice(80);
        priceRangeBoList.add(priceRangeBo3);
        PriceRangeBo priceRangeBo4 = new PriceRangeBo();
        priceRangeBo.setDownPrice(1000);
        priceRangeBo.setUpPrice(3000);
        priceRangeBo.setMinReductionPrice(50);
        priceRangeBo.setMaxReductionPrice(200);
        priceRangeBoList.add(priceRangeBo4);
        PriceRangeBo priceRangeBo5 = new PriceRangeBo();
        priceRangeBo.setDownPrice(3000);
        priceRangeBo.setUpPrice(3000);
        priceRangeBo.setMinReductionPrice(80);
        priceRangeBo.setMaxReductionPrice(400);
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

    // 非自营定价处理
    void processGoods(Goods goods) {
        Row row = goods.getRow();
        // 计算dm
        double dm = Double.min(getMaxReduction(goods.getPagePrice()), discount * goods.getPagePrice());
        // 计算降价幅度
        double down = dm * noSelfRationMap.get(goods.getGrade());
        double resultPrice = goods.getPagePrice() - down;
        // 校验拼团价是否满足最小降价幅度
        double minReduction = getMinReduction(goods.getPagePrice());
        if ((goods.getPagePrice() - resultPrice) < minReduction) {
            goods.setResultPrice(goods.getPagePrice() - minReduction);
        }
        // 尾数美化，美化完成后校验区间，不符合要求不进行美化
    }

    private double getMaxReduction(double pagePrice) {
        // 获取最高降价幅度
        for (PriceRangeBo priceRangeBo : priceRangeBoList) {
            if (pagePrice < priceRangeBo.getUpPrice()) {
                return priceRangeBo.getMaxReductionPrice();
            }
        }
        return 0;
    }

    private double getMinReduction(double pagePrice) {
        // 获取最高降价幅度
        for (PriceRangeBo priceRangeBo : priceRangeBoList) {
            if (pagePrice < priceRangeBo.getUpPrice()) {
                return priceRangeBo.getMinReductionPrice();
            }
        }
        return 0;
    }
}
