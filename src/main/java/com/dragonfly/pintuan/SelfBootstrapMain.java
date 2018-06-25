package com.dragonfly.pintuan;

import java.io.IOException;

public class SelfBootstrapMain {
    public static void main(String[] args) throws IOException {
        ExcelService excelService = new ExcelService();
        excelService.readExcel();
    }
}
