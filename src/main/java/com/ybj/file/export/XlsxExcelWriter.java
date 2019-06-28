package com.ybj.file.export;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class XlsxExcelWriter extends ExcelWriter {
    @Override
    protected Workbook createWorkbook(int flushSize) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(flushSize);
        return workbook;
    }

    @Override
    protected int getMaxSheetSize() {
        return 1000000;
    }
}
