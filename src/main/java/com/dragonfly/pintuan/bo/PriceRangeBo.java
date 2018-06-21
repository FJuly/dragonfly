package com.dragonfly.pintuan.bo;

public class PriceRangeBo {

    private double downPrice;
    private double upPrice;

    private double minReductionPrice; // 最小降价
    private double maxReductionPrice; // 最大降价


    private PriceRangeBo(Builder builder) {
        this.downPrice = builder.downPrice;
        this.upPrice = builder.upPrice;
        this.minReductionPrice = builder.minReductionPrice;
        this.maxReductionPrice = builder.maxReductionPrice;
    }

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


    public static class Builder {
        private double downPrice;
        private double upPrice;

        private double minReductionPrice; // 最小降价
        private double maxReductionPrice; // 最大降价

        public Builder withDownPrice(double downPrice) {
            this.downPrice = downPrice;
            return this;
        }

        public Builder withUpPrice(double upPrice) {
            this.upPrice = upPrice;
            return this;
        }

        public Builder withMinReductionPrice(double minReductionPrice) {
            this.minReductionPrice = minReductionPrice;
            return this;
        }

        public Builder withMaxReductionPrice(double maxReductionPrice) {
            this.maxReductionPrice = maxReductionPrice;
            return this;
        }

        public PriceRangeBo build() {
            return new PriceRangeBo(this);
        }
    }
}
