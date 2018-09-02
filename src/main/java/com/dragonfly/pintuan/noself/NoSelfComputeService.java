package com.dragonfly.pintuan.noself;

import com.dragonfly.pintuan.AbstractComputeService;
import com.dragonfly.pintuan.bo.Goods;
import com.dragonfly.pintuan.bo.PriceRangeBo;


// 非自营定价处理
public class NoSelfComputeService extends AbstractComputeService {

    public void processGoods(Goods goods) {
        // 各种剔除条件

        // 计算dm
        double dm = Double.min(getMaxReduction(goods.getPagePrice()), NoSelfConfig.discount * goods.getPagePrice());
        // 计算降价幅度
        double down = dm * NoSelfConfig.noSelfRationMap.get(goods.getGrade());
        double resultPrice = goods.getPagePrice() - down;
        goods.setResultPrice(resultPrice);
        // 校验拼团价是否满足最小降价幅度
        if (!isSuitPriceGap(resultPrice, goods.getPagePrice())) {
            double gap = getMinReduction(goods.getPagePrice());
            double resultGapPrice = goods.getPagePrice() - gap;
            if (!checkPriceRange(resultGapPrice, dm, goods.getPagePrice())) {
                goods.setRemark("调整为最小差价后不符合价格区间");
            } else {
                goods.setResultPrice(resultGapPrice);
            }
        }
        double beautifulPrice = beautifyPrice(goods.getResultPrice());
        if (checkPriceRange(beautifulPrice, dm, goods.getPagePrice()) && isSuitPriceGap(beautifulPrice, goods.getPagePrice())) {
            goods.setResultPrice(beautifulPrice);
        } else {
            goods.setResultPrice(halfUp(goods.getResultPrice()));
            if (!(checkPriceRange(goods.getResultPrice(), dm, goods.getPagePrice()) && isSuitPriceGap(goods.getResultPrice(), goods.getPagePrice()))) {
                goods.setRemark("尾数优化后不符合区间，不优化，价格四舍五入取整不符合区间");
            } else {
                goods.setRemark("尾数优化后不符合区间，四舍五入取整符合区间");
            }
        }
    }

    // 获取最高降价幅度
    private double getMaxReduction(double pagePrice) {
        for (PriceRangeBo priceRangeBo : NoSelfConfig.priceRangeBoList) {
            if (pagePrice < priceRangeBo.getUpPrice()) {
                return priceRangeBo.getMaxReductionPrice();
            }
        }
        return 0;
    }

    // 获最小降价幅度
    private double getMinReduction(double pagePrice) {
        for (PriceRangeBo priceRangeBo : NoSelfConfig.priceRangeBoList) {
            if (pagePrice < priceRangeBo.getUpPrice()) {
                return priceRangeBo.getMinReductionPrice();
            }
        }
        return 0;
    }
    private boolean isSuitPriceGap(double resultPrice, double pagePrice) {
        double minReduction = getMinReduction(pagePrice);
        return ((pagePrice - resultPrice) >= minReduction);
    }
}
