package com.dragonfly.pintuan.bo;

import org.apache.poi.ss.usermodel.Row;

public class Goods {
    private String skuId;
    private String grade;
    private String firstCategoryStr;
    private String brandStr;
    private double cost;
    private double pagePrice;
    private double frontStore;
    private double sales;
    private double cmtPrice;
    private double minPrice;
    private double maxPrice;
    private double ration;  // 降价系数
    private double directDownMax; // 最多降价
    private double rulePrice1;
    private double rulePrice2;
    private double rulePrice3;
    private double resultPrice;
    private double minSpread; // 最小差价
    private double downMax;
    private String remark;
    private Row row;// 该行记录在excel中所在的行

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFirstCategoryStr() {
        return firstCategoryStr;
    }

    public void setFirstCategoryStr(String firstCategoryStr) {
        this.firstCategoryStr = firstCategoryStr;
    }

    public String getBrandStr() {
        return brandStr;
    }

    public void setBrandStr(String brandStr) {
        this.brandStr = brandStr;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPagePrice() {
        return pagePrice;
    }

    public void setPagePrice(double pagePrice) {
        this.pagePrice = pagePrice;
    }

    public double getFrontStore() {
        return frontStore;
    }

    public void setFrontStore(double frontStore) {
        this.frontStore = frontStore;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public double getCmtPrice() {
        return cmtPrice;
    }

    public void setCmtPrice(double cmtPrice) {
        this.cmtPrice = cmtPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getRulePrice1() {
        return rulePrice1;
    }

    public void setRulePrice1(double rulePrice1) {
        this.rulePrice1 = rulePrice1;
    }

    public double getRulePrice2() {
        return rulePrice2;
    }

    public void setRulePrice2(double rulePrice2) {
        this.rulePrice2 = rulePrice2;
    }

    public double getRulePrice3() {
        return rulePrice3;
    }

    public void setRulePrice3(double rulePrice3) {
        this.rulePrice3 = rulePrice3;
    }

    public double getResultPrice() {
        return resultPrice;
    }

    public void setResultPrice(double resultPrice) {
        this.resultPrice = resultPrice;
    }

    public double getRation() {
        return ration;
    }

    public void setRation(double ration) {
        this.ration = ration;
    }

    public double getDirectDownMax() {
        return directDownMax;
    }

    public void setDirectDownMax(double directDownMax) {
        this.directDownMax = directDownMax;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public double getMinSpread() {
        return minSpread;
    }

    public void setMinSpread(double minSpread) {
        this.minSpread = minSpread;
    }

    public double getDownMax() {
        return downMax;
    }

    public void setDownMax(double downMax) {
        this.downMax = downMax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}