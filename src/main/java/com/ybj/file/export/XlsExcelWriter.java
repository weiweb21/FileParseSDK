package com.cmiot.mng.file.export;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class XlsExcelWriter extends ExcelWriter {

    @Override
    protected Workbook createWorkbook(int flushSize) {
        return new HSSFWorkbook();
    }

    @Override
    protected int getMaxSheetSize() {
        return 65536;
    }

}
