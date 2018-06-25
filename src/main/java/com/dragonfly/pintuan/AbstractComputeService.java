package com.dragonfly.pintuan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractComputeService {

    private static Map<Integer, Integer> tailMap = new HashMap<>();

    static {
        tailMap.put(0, -1);
        tailMap.put(1, -2);
        tailMap.put(2, -3);
        tailMap.put(3, 2);
        tailMap.put(4, 1);
        tailMap.put(5, 0);
        tailMap.put(6, 0);
        tailMap.put(7, 1);
        tailMap.put(8, 0);
        tailMap.put(9, 0);
    }

    protected double beautifyPrice(double resultPrice) {
        BigDecimal decimal = new BigDecimal(resultPrice);
        if (resultPrice >= 13) {
            // 最终价格 >=13 四舍五入去除小数点
            int intResultPrice = decimal.setScale(0, RoundingMode.HALF_UP).intValue();
            int tail = intResultPrice % 10;
            resultPrice = intResultPrice + tailMap.get(tail);
        } else {
            // 小于13小数尾数直接优化尾数直接优化成0.9
            int intResultPrice = decimal.setScale(0, BigDecimal.ROUND_DOWN).intValue();
            return intResultPrice + 0.9;
        }
        return resultPrice;
    }

    protected boolean checkPriceRange(double resultPrice, double downMax, double pagePrice) {
        return resultPrice >= (pagePrice - downMax) && resultPrice <= pagePrice;
    }

    protected double halfUp(double num) {
        return BigDecimal.valueOf(num).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }
}
