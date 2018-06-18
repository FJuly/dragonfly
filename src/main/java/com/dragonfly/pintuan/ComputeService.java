package com.dragonfly.pintuan;

import com.dragonfly.pintuan.bo.Goods;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

class ComputeService {

    private static Logger logger = LoggerFactory.getLogger(ComputeService.class);

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

    void processGoods(Goods goods) {
        Row row = goods.getRow();
        double discount = CmtConfig.discountMap.get(goods.getFirstCategoryStr());
        double downMax = Double.min(Double.min(goods.getPagePrice() * discount, CmtConfig.constantY),
                goods.getPagePrice() - goods.getCost() - 1);
        goods.setDownMax(downMax);

        if (CmtConfig.rationMap.get(goods.getGrade()) == null) {
            logger.error("grade err,skuId->{},row->{}", goods.getSkuId(), row.getRowNum());
            goods.setRemark("商品不存在等级分类");
            return;
        }
        double ration = CmtConfig.rationMap.get(goods.getGrade());
        goods.setRation(ration);

        // 计算rule1价格
        double rulePrice1 = goods.getPagePrice() - downMax * ration;
        rulePrice1 = halfUp(rulePrice1);
        goods.setRulePrice1(rulePrice1);
        // 计算rule2价格a
        double rulePrice2 = CmtConfig.rule2Param * (goods.getMaxPrice() - goods.getMinPrice()) + goods.getMinPrice();
        rulePrice2 = halfUp(rulePrice2);
        // 计算rule3价格
        double rulePrice3;
        if (goods.getSales() != 0) {
            rulePrice3 = getRulePrice3(goods);
        } else {
            rulePrice3 = goods.getPagePrice() - downMax;
        }
        // 计算最终定价
        if (checkPriceRange(rulePrice2, downMax, goods.getPagePrice())) {
            goods.setResultPrice(rulePrice2);
            logger.info("最终建议价为rulePrice2,skuId={},rowNum={}", goods.getSkuId(), row.getRowNum());
        } else {
            goods.setResultPrice((rulePrice1 + rulePrice3) / 2);
            logger.info("最终建议价为(rulePrice1+rulePrice3),skuId={}", goods.getSkuId());
        }
        // 检查是否满足价差
        if (!isSuitPriceGap(goods.getResultPrice(), goods.getPagePrice())) {
            double gap = getGap(goods.getPagePrice());
            double resultGapPrice = goods.getPagePrice() - gap;
            if (!checkPriceRange(resultGapPrice, downMax, goods.getPagePrice())) {
                logger.error("调整为最小差价后不符合价格区间, skuId={},rowNum={}", goods.getSkuId(), row.getRowNum());
                goods.setRemark("调整为最小差价后不符合价格区间");
                return;
            } else {
                goods.setResultPrice(resultGapPrice);
            }
        }
        //进行尾数优化
        double beautifulPrice = beautifyPrice(goods.getResultPrice());
        if (checkPriceRange(beautifulPrice, downMax, goods.getPagePrice())
                && isSuitPriceGap(beautifulPrice, goods.getPagePrice())) {
            goods.setResultPrice(beautifulPrice);
        } else {
            goods.setRemark("尾数优化后不符合区间，不进行优化");
        }
    }

    private double getRulePrice3(Goods goods) {
        double rulePrice3;
        int salesByDay = BigDecimal.valueOf((goods.getFrontStore() / goods.getSales()) * 30).intValue(); // 库存天期
        if (salesByDay <= CmtConfig.startDay)
            rulePrice3 = goods.getPagePrice();
        else if (salesByDay >= CmtConfig.endDay)
            rulePrice3 = goods.getPagePrice() - goods.getDownMax();
        else {
            // y = ax + b, 两组输入计算(a,b), [90, pagePrice],[360, pagePrice - downMax]
            double a = goods.getDownMax() / (CmtConfig.startDay - CmtConfig.endDay);
            double b = goods.getPagePrice() - a * CmtConfig.startDay;
            logger.info("线性方程->pagePrice:{},downMax:{},a:{},b:{},skuId:{},rowNum:{}", goods.getPagePrice(),
                    goods.getDownMax(), a, b, goods.getSkuId(), goods.getRow().getRowNum());
            rulePrice3 = halfUp(a * salesByDay + b);
        }
        return rulePrice3;
    }

    private double halfUp(double num) {
        return BigDecimal.valueOf(num).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    private double beautifyPrice(double resultPrice) {
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

    private boolean isSuitPriceGap(double resultPrice, double pagePrice) {
        double gap = pagePrice - resultPrice;
        return gap >= getGap(pagePrice);
    }

    private double getGap(double pagePrice) {
        for (Map.Entry<Double, Double> mp : CmtConfig.minSpreadMap.entrySet()) {
            if (pagePrice <= mp.getKey()) {
                return mp.getValue();
            }
        }
        return CmtConfig.minSpreadMap.get(-1d);
    }

    private boolean checkPriceRange(double resultPrice, double downMax, double pagePrice) {
        return resultPrice >= (pagePrice - downMax) && resultPrice <= pagePrice;
    }
}