package com.dragonfly.pintuan.bo;

public class PriceRangeBo {

    private double downPrice;
    private double upPrice;

    private double minReductionPrice; // 最小降价
    private double maxReductionPrice; // 最大降价

    public double getDownPrice() {
        return downPrice;
    }

    public void setDownPrice(double downPrice) {
        this.downPrice = downPrice;
    }

    public double getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(double upPrice) {
        this.upPrice = upPrice;
    }

    public double getMinReductionPrice() {
        return minReductionPrice;
    }

    public void setMinReductionPrice(double minReductionPrice) {
        this.minReductionPrice = minReductionPrice;
    }

    public double getMaxReductionPrice() {
        return maxReductionPrice;
    }

    public void setMaxReductionPrice(double maxReductionPrice) {
        this.maxReductionPrice = maxReductionPrice;
    }
}
