package com.dragonfly.pintuan.self;

import java.io.IOException;

public class BootstrapMain {
    public static void main(String[] args) throws IOException {
        ExcelService excelService = new ExcelService();
        excelService.readExcel();
    }
}
