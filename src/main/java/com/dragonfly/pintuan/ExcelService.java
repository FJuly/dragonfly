package com.dragonfly.pintuan;

import com.dragonfly.pintuan.bo.Goods;
import com.dragonfly.pintuan.noself.NoSelfComputeService;
import com.dragonfly.pintuan.self.SelfComputeService;
import com.dragonfly.pintuan.self.SelfConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelService {

    private NoSelfComputeService computeService = new NoSelfComputeService();

    void readExcel() throws IOException {
        Workbook workbook = null;
        try {
            workbook = getReadWorkBookType(SelfConfig.filePath);
            if (workbook == null) {
                return;
            }
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum < SelfConfig.rowNums; rowNum++) {
                Row row = sheet.getRow(rowNum);
                Goods goods = new Goods();
                initGood(goods, row);
                computeService.processGoods(goods);
                Cell failReasonCell = row.createCell(SelfConfig.failReasonIndex);
                Cell priceCell = row.createCell(SelfConfig.resultPriceIndex);
                priceCell.setCellValue(goods.getResultPrice());
                failReasonCell.setCellValue(goods.getRemark());
            }
            FileOutputStream os = new FileOutputStream(SelfConfig.filePath);
            workbook.write(os);
            os.close();
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    private Workbook getReadWorkBookType(String filePath) throws IOException {
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

    private void initGood(Goods goods, Row row) {
        goods.setRow(row);
        if (SelfConfig.skuIdIndex != -1) {
            goods.setSkuId(getCellStringVal(row.getCell(SelfConfig.skuIdIndex)));
        }
        if (SelfConfig.gradeIndex != -1) {
            goods.setGrade(getCellStringVal(row.getCell(SelfConfig.gradeIndex)));
        }
        if (SelfConfig.costIndex != -1) {
            goods.setCost(Double.valueOf(getCellStringVal(row.getCell(SelfConfig.costIndex))));
        }
        if (SelfConfig.pagePriceIndex != -1) {
            goods.setPagePrice(Double.valueOf(getCellStringVal(row.getCell(SelfConfig.pagePriceIndex))));
        }
        if (SelfConfig.frontStoreIndex != -1) {
            goods.setFrontStore(Double.valueOf(getCellStringVal(row.getCell(SelfConfig.frontStoreIndex))));
        }
        if (SelfConfig.firstCategoryIndex != -1) {
            goods.setFirstCategoryStr(getCellStringVal(row.getCell(SelfConfig.firstCategoryIndex)));
        }
        if (SelfConfig.brandIndex != -1) {
            goods.setBrandStr(getCellStringVal(row.getCell(SelfConfig.brandIndex)));
        }
        if (SelfConfig.salesIndex != -1 && row.getCell(SelfConfig.salesIndex) != null) {
            String salesStr = getCellStringVal(row.getCell(SelfConfig.salesIndex));
            if (!salesStr.equals("null")) {
                goods.setSales(Double.valueOf(salesStr));
            }
        }
        if (SelfConfig.cmtPriceIndex != -1 && row.getCell(SelfConfig.cmtPriceIndex) != null) {
            String cmtPriceStr = getCellStringVal(row.getCell(SelfConfig.cmtPriceIndex));
            if (!cmtPriceStr.equals("null")) {
                goods.setCmtPrice(Double.valueOf(cmtPriceStr));
            }
        }
        if (SelfConfig.minPriceIndex != -1 && row.getCell(SelfConfig.minPriceIndex) != null) {
            String minPriceStr = getCellStringVal(row.getCell(SelfConfig.minPriceIndex));
            if (!minPriceStr.equals("null")) {
                goods.setMinPrice(Double.valueOf(minPriceStr));
            }
        }
        if (SelfConfig.maxPriceIndex != -1 && row.getCell(SelfConfig.maxPriceIndex) != null) {
            String maxPriceStr = getCellStringVal(row.getCell(SelfConfig.maxPriceIndex));
            if (!maxPriceStr.equals("null")) {
                goods.setMaxPrice(Double.valueOf(maxPriceStr));
            }
        }
    }

    private String getCellStringVal(Cell cell) {
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
}
