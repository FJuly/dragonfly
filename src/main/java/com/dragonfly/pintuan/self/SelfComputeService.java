package com.dragonfly.pintuan.self;

import com.dragonfly.pintuan.AbstractComputeService;
import com.dragonfly.pintuan.bo.Goods;
import com.dragonfly.pintuan.bo.PriceRangeBo;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class SelfComputeService extends AbstractComputeService {

    private static Logger logger = LoggerFactory.getLogger(SelfComputeService.class);

    public void processGoods(Goods goods) {
        boolean isRule2 = false;
        boolean isSuit = false;

        // 各种剔除条件
        if (!preCheck(goods))
            return;
        Row row = goods.getRow();
        double discount = SelfConfig.discountMap.get(goods.getFirstCategoryStr());
        double downMax = Double.min(Double.min(goods.getPagePrice() * discount,
                SelfConfig.maxSpreadYMap.get(goods.getFirstCategoryStr())),
                goods.getPagePrice() - goods.getCost() - 1);
        goods.setDownMax(downMax);

        if (SelfConfig.rationMap.get(goods.getGrade()) == null) {
            logger.error("grade err,skuId->{},row->{}", goods.getSkuId(), row.getRowNum());
            goods.setRemark("商品不存在等级分类");
            return;
        }
        double ration = SelfConfig.rationMap.get(goods.getGrade());
        goods.setRation(ration);

        // 计算rule1价格
        double rulePrice1 = goods.getPagePrice() - downMax * ration;
        // 计算rule2价格
        double rulePrice2 = SelfConfig.rule2Param * (goods.getMaxPrice() - goods.getMinPrice()) + goods.getMinPrice();
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
            isRule2 = true;
        } else {
            goods.setResultPrice((rulePrice1 + rulePrice3) / 2);
        }
        // 检查是否满足价差
        if (!isSuitPriceGap(goods.getResultPrice(), goods.getPagePrice())) {
            double gap = getMinSpreadPrice(goods.getPagePrice());
            double resultGapPrice = goods.getPagePrice() - gap;
            if (!checkPriceRange(resultGapPrice, downMax, goods.getPagePrice())) {
                logger.error("调整为最小差价后不符合价格区间, skuId={},rowNum={}", goods.getSkuId(), row.getRowNum());
                goods.setRemark("调整为最小差价后不符合价格区间");
                goods.setResultPrice(halfUp(goods.getResultPrice()));
                return;
            } else {
                goods.setResultPrice(resultGapPrice);
                isSuit = true;
            }
        }
        //进行尾数优化
        double beautifulPrice = beautifyPrice(goods.getResultPrice());
        if (checkPriceRange(beautifulPrice, downMax, goods.getPagePrice()) && isSuitPriceGap(beautifulPrice, goods.getPagePrice())) {
            goods.setResultPrice(beautifulPrice);
            if (isRule2 && !isSuit && goods.getMinPrice() != 0) {
                logger.info("test:rule1Price:{}, rule2Price:{}, rule3Price:{},resultPrice:{},skuId={},row:{}", rulePrice1, rulePrice2, rulePrice3, goods.getSkuId(), beautifulPrice, row.getRowNum());
            }
        } else {
            goods.setResultPrice(halfUp(goods.getResultPrice()));
            if (!(checkPriceRange(goods.getResultPrice(), downMax, goods.getPagePrice()) && isSuitPriceGap(goods.getResultPrice(), goods.getPagePrice()))) {
                goods.setRemark("尾数优化后不符合区间，不优化，价格四舍五入取整不符合区间");
            } else {
                goods.setRemark("尾数优化后不符合区间，四舍五入取整符合区间");
            }
        }
        logger.info("rule1Price:{}, rule2Price:{}, rule3Price:{},skuId={},row:{}", rulePrice1, rulePrice2, rulePrice3, goods.getSkuId(), row.getRowNum());
    }

    private double getRulePrice3(Goods goods) {
        double rulePrice3;
        int salesByDay = BigDecimal.valueOf((goods.getFrontStore() / goods.getSales()) * 30).intValue(); // 库存天期
        if (salesByDay <= SelfConfig.startDay)
            rulePrice3 = goods.getPagePrice();
        else if (salesByDay >= SelfConfig.endDay)
            rulePrice3 = goods.getPagePrice() - goods.getDownMax();
        else {
            // y = ax + b, 两组输入计算(a,b), [90, pagePrice],[360, pagePrice - downMax]
            double a = goods.getDownMax() / (SelfConfig.startDay - SelfConfig.endDay);
            double b = goods.getPagePrice() - a * SelfConfig.startDay;
            logger.info("线性方程->pagePrice:{},downMax:{},a:{},b:{},skuId:{},rowNum:{}", goods.getPagePrice(),
                    goods.getDownMax(), a, b, goods.getSkuId(), goods.getRow().getRowNum());
            rulePrice3 = a * salesByDay + b;
        }
        return rulePrice3;
    }

    private boolean isSuitPriceGap(double resultPrice, double pagePrice) {
        double gap = pagePrice - resultPrice;
        return gap >= getMinSpreadPrice(pagePrice);
    }

    // 获取最小降价幅度
    private double getMinSpreadPrice(double pagePrice) {
        for (PriceRangeBo priceRangeBo : SelfConfig.minSpreadList) {
            if (pagePrice < priceRangeBo.getUpPrice()) {
                return priceRangeBo.getMinReductionPrice();
            }
        }
        return 0;
    }

    private boolean preCheck(Goods goods) {
        if (SelfConfig.excludeBrandList.contains(goods.getBrandStr())) {
            goods.setRemark("剔除品牌");
            return false;
        }
        if (SelfConfig.secondCateList.contains(goods.getSecondCategoryStr())) {
            goods.setRemark("剔除类目");
            return false;
        }
        if (SelfConfig.thirdCateList.contains(goods.getThirdCategoryStr())) {
            goods.setRemark("剔除类目");
            return false;
        }
        return true;
    }
}