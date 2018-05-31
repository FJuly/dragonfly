package com.dragonfly;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class ExcelTest {

    private static Logger logger = LoggerFactory.getLogger(ExcelTest.class);

    private final static int countRows = 80320;
    private final static int gradeIndex = 6;
    private final static int costIndex = 8;
    private final static int pagePriceIndex = 10;
    private final static int minPriceIndex = 11;
    private final static int maxPriceIndex = 12;
    private final static int skuIdIndex = 0;
    private final static int failReasonIndex = 17;
    private final static int resultPriceIndex = 16;
    private final static int frontStoreIndex = 7;
    private final static int salesIndex = 9;
    private final static int cmtPriceIndex = 13;


    private final static String filePath = "/Users/fanggang/CDE.xlsx";

    private static Map<Integer, Integer> tailMap = new HashMap<>();
    private static Map<Integer, Integer> cmtTailMap = new HashMap<>();
    private static Map<String, Double> rationMap = new HashMap<>();


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

        cmtTailMap.put(0, -1);
        cmtTailMap.put(1, -2);
        cmtTailMap.put(2, -3);
        cmtTailMap.put(3, -4);
        cmtTailMap.put(4, -5);
        cmtTailMap.put(5, -6);
        cmtTailMap.put(6, -1);
        cmtTailMap.put(7, -1);
        cmtTailMap.put(8, -2);
        cmtTailMap.put(9, -1);

        rationMap.put("A", 0.2);
        rationMap.put("A-", 0.2);
        rationMap.put("新品", 0.2);
        rationMap.put("B", 0.4);
        rationMap.put("B-", 0.4);
        rationMap.put("C", 0.6);
        rationMap.put("D", 0.8);
        rationMap.put("E", 1.0);

    }

    public static void main(String[] args) {
        try {
            readExcel();
        } catch (Exception e) {
            logger.error("exception：", e);
        }
    }

    private static void readExcel() throws IOException {
        Workbook workbook = null;
        try {
            workbook = getReadWorkBookType(filePath);
            if (workbook == null) {
                return;
            }
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum < countRows; rowNum++) {
                Row row = sheet.getRow(rowNum);
                processRow(row);
            }
            FileOutputStream os = new FileOutputStream(filePath);
            workbook.write(os);
            os.close();
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    private static Workbook getReadWorkBookType(String filePath) throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (filePath.toLowerCase().endsWith("xlsx")) {
                return new XSSFWorkbook(is);
            } else if (filePath.toLowerCase().endsWith("xls")) {
                return new HSSFWorkbook(is);
            } else {
                return null;
            }
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private static String getCellStringVal(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            default:
                return StringUtils.EMPTY;
        }
    }

    private static void processRow(Row row) {
        String skuId;
        String grade; // 商品等级
        double cost; // 商品成本价
        double pagePrice; // 页面价
        double frontStore;// 库存
        double sales = 0; // 30天销售量
        double cmtPrice = 0; // 竞品价格

        double downMax; // 下降的最大价格
        double ration;  // 降价系数，不同等级商品价格幅度不一样
        double minPrice = 0; // 价格模型最低值
        double maxPrice = 0; // 价格模型最大值
        double rulePrice1; // 规则1价格
        double rulePrice2; // 规则2价格
        double rulePrice3;
        double resultPrice; // 最终建议价

        skuId = getCellStringVal(row.getCell(skuIdIndex));
        grade = getCellStringVal(row.getCell(gradeIndex));
        cost = Double.valueOf(getCellStringVal(row.getCell(costIndex)));
        pagePrice = Double.valueOf(getCellStringVal(row.getCell(pagePriceIndex)));
        frontStore = Double.valueOf(getCellStringVal(row.getCell(frontStoreIndex)));

        String salesStr = getCellStringVal(row.getCell(salesIndex));
        if (!salesStr.equals("null")) {
            sales = Double.valueOf(salesStr);
        }
        String cmtPriceStr = getCellStringVal(row.getCell(cmtPriceIndex));
        if (!cmtPriceStr.equals("null")) {
            cmtPrice = Double.valueOf(cmtPriceStr);
        }
        String minPriceStr = getCellStringVal(row.getCell(minPriceIndex));
        if (!minPriceStr.equals("null")) {   // 各种异常判断下
            minPrice = Double.valueOf(minPriceStr);
        }
        String maxPriceStr = getCellStringVal(row.getCell(maxPriceIndex));
        if (!maxPriceStr.equals("null")) {
            maxPrice = Double.valueOf(maxPriceStr);
        }

        // 计算downMax
        downMax = Double.min(Double.min(pagePrice * 0.1, 100), pagePrice - cost - 1);
        // 计算降价系数
        if (rationMap.get(grade) == null) {
            logger.error("grade err,skuId->{},row->{}", skuId, row.getRowNum());
            Cell cell = row.createCell(failReasonIndex);
            cell.setCellValue("商品不存在等级分类");
            return;
        }
        ration = rationMap.get(grade);

        // 计算rule1价格
        rulePrice1 = pagePrice - downMax * ration;
        rulePrice1 = halfUp(rulePrice1);
        // 计算rule2价格
        rulePrice2 = 0.2 * (maxPrice - minPrice) + minPrice;
        rulePrice2 = halfUp(rulePrice2);
        // 计算rule3价格
        if (sales != 0) {
            rulePrice3 = getRulePrice3(pagePrice, frontStore, sales, downMax);
        } else {
            rulePrice3 = pagePrice - downMax;
        }
        // 计算最终定价
        if (rulePrice2 >= (pagePrice - downMax) && rulePrice2 <= (pagePrice)) {
            resultPrice = rulePrice2;
            logger.info("最终建议价为rulePrice2,skuId={}", skuId);
        } else {
            resultPrice = halfUp((rulePrice1 + rulePrice3) / 2);
            logger.info("最终建议价为(rulePrice1+rulePrice3),skuId={}", skuId);
        }

        // 与竞品价格进行比较，获取最终价格
        if (cmtPrice != 0 && resultPrice >= cmtPrice) {
            double cmtResultPrice = compareWithCmt(cmtPrice);
            if (checkPrice(cmtResultPrice, cost, pagePrice) && checkBeautifulPrice(cmtResultPrice, pagePrice)) {
                Cell priceCell = row.createCell(resultPriceIndex);
                priceCell.setCellValue(cmtResultPrice);
                logger.info("竞品价格优化,skuId->{},rule1->{},rule2->{},rule3->{},cmtResultPrice->{},rowNum={}", skuId, rulePrice1, rulePrice2, rulePrice3, cmtResultPrice, row.getRowNum());
                return;
            }
        }
        // 如果与竞品价格相比不符合要求，直接美化
        resultPrice = beautifyPrice(resultPrice);

        if (!(checkPrice(resultPrice, cost, pagePrice) && checkBeautifulPrice(resultPrice, pagePrice))) {
            logger.info("降价范围不符合条件,skuId->{},rule1->{},rule2->{},rule3->{},resultPrice->{},rowNum={}", skuId, rulePrice1, rulePrice2, rulePrice3, resultPrice, row.getRowNum());
            Cell failReasonCell = row.createCell(failReasonIndex);
            Cell priceCell = row.createCell(resultPriceIndex);
            priceCell.setCellValue(resultPrice);
            failReasonCell.setCellValue("降价范围不符合条件");
            return;
        }

        Cell cell = row.createCell(resultPriceIndex);
        cell.setCellValue(resultPrice);
        logger.info("skuId->{},rule1->{}, rule2->{},rule3->{},resultPrice->{},rowNum={}", skuId, rulePrice1, rulePrice2, rulePrice3, resultPrice, row.getRowNum());
    }

    private static double getRulePrice3(double pagePrice, double frontStore, double sales, double downMax) {
        double rulePrice3;
        int salesByDay = BigDecimal.valueOf((frontStore / sales) * 30).intValue(); // 库存天期
        if (salesByDay <= 90)
            rulePrice3 = pagePrice;
        else if (salesByDay >= 360)
            rulePrice3 = pagePrice - downMax;
        else {
            // y = ax + b, 两组输入计算(a,b), [90, pagePrice],[360, pagePrice - downMax]
            double a = downMax / -270;
            double b = pagePrice - a * 90;
            logger.info("线性方程->pagePrice:{},downMax:{},a:{},b:{}", pagePrice, downMax, a, b);
            rulePrice3 = halfUp(a * salesByDay + b);
        }
        return rulePrice3;
    }

    // 与竞品价格相比 且 优化成比竞品价低 且 符合尾数优化要求
    private static double compareWithCmt(double cmtPrice) {
        if (cmtPrice == 0)
            return 0;
        if (cmtPrice >= 16) {
            int intResultPrice = BigDecimal.valueOf(cmtPrice).setScale(0, RoundingMode.HALF_UP).intValue();
            int tail = intResultPrice % 10;
            return intResultPrice + cmtTailMap.get(tail);
        } else {
            return BigDecimal.valueOf(cmtPrice).setScale(0, BigDecimal.ROUND_DOWN).
                    subtract(BigDecimal.valueOf(0.1)).doubleValue();
        }
    }

    private static double halfUp(double num) {
        return BigDecimal.valueOf(num).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    private static double beautifyPrice(double resultPrice) {
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

    // 校验价格是否满足条件
    private static boolean checkPrice(double resultPrice, double cost, double pagePrice) {
        // 是否低于成本价，区间是否封闭
        if (resultPrice < cost) {
            return false;
        }
        // 10% 100
        return pagePrice >= resultPrice && (BigDecimal.valueOf(pagePrice).subtract(BigDecimal.valueOf(resultPrice)).doubleValue()) <= 100
                && ((BigDecimal.valueOf(pagePrice).subtract(BigDecimal.valueOf(resultPrice)).doubleValue())) <= pagePrice * 0.1;
    }

    // 校验美化后的价格，降价幅度要小于0.9
    private static boolean checkBeautifulPrice(double resultPrice, double pagePrice) {
        return Math.abs((BigDecimal.valueOf(pagePrice).subtract(BigDecimal.valueOf(resultPrice)).doubleValue())) >= 0.9;
    }
}
