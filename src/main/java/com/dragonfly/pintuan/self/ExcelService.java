package com.dragonfly.pintuan.self;

import com.dragonfly.pintuan.bo.Goods;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelService {

    private ComputeService computeService = new ComputeService();

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
        goods.setSkuId(getCellStringVal(row.getCell(SelfConfig.skuIdIndex)));
        goods.setGrade(getCellStringVal(row.getCell(SelfConfig.gradeIndex)));
        goods.setCost(Double.valueOf(getCellStringVal(row.getCell(SelfConfig.costIndex))));
        goods.setPagePrice(Double.valueOf(getCellStringVal(row.getCell(SelfConfig.pagePriceIndex))));
        goods.setFrontStore(Double.valueOf(getCellStringVal(row.getCell(SelfConfig.frontStoreIndex))));
        goods.setFirstCategoryStr(getCellStringVal(row.getCell(SelfConfig.firstCategoryIndex)));
        goods.setBrandStr(getCellStringVal(row.getCell(SelfConfig.brandIndex)));
        if (row.getCell(SelfConfig.salesIndex) != null) {
            String salesStr = getCellStringVal(row.getCell(SelfConfig.salesIndex));
            if (!salesStr.equals("null")) {
                goods.setSales(Double.valueOf(salesStr));
            }
        }
        if (row.getCell(SelfConfig.cmtPriceIndex) != null) {
            String cmtPriceStr = getCellStringVal(row.getCell(SelfConfig.cmtPriceIndex));
            if (!cmtPriceStr.equals("null")) {
                goods.setCmtPrice(Double.valueOf(cmtPriceStr));
            }
        }
        if (row.getCell(SelfConfig.minPriceIndex) != null) {
            String minPriceStr = getCellStringVal(row.getCell(SelfConfig.minPriceIndex));
            if (!minPriceStr.equals("null")) {
                goods.setMinPrice(Double.valueOf(minPriceStr));
            }
        }
        if (row.getCell(SelfConfig.maxPriceIndex) != null) {
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
