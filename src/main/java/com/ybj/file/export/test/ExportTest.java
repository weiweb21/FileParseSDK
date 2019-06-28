package com.ybj.file.export.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ybj.file.export.ExcelExportUtils;
import com.ybj.file.export.templates.SheetWriteTemplate;

public class ExportTest {

    public static void main(String[] args) {
        try {
            OutputStream out = new FileOutputStream(new File("D://test.xls"));
            Map<String, String> header = new LinkedHashMap<>();
            header.put("name", "姓名");
            header.put("age", "年龄");
            header.put("sex", "性别");
            List<Map<String, Object>> dataList = new ArrayList<>();
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("name", "颜本军放大法师打发第三方大师法第三方发生");
            data.put("age", 33);
            data.put("time", new Date());
            dataList.add(data);
            // ExcelExportUtils.exportXlsWithoutHead(out, dataList);

            OutputStream out2 = new FileOutputStream(new File("D://test1.xlsx"));

            List<SheetWriteTemplate> templates = new ArrayList<>();
            SheetWriteTemplate t1 = new SheetWriteTemplate();
            t1.addAll(header);
            t1.setPageDataQuery((pageNum) -> {
                while (pageNum < 100000) {
                    return dataList;
                }
                return null;
            });
            SheetWriteTemplate t2 = new SheetWriteTemplate();
            t2.addAll(header);
            t2.setPageDataQuery((pageNum) -> {
                return pageNum == 0 ? dataList : null;
            });
            templates.add(t1);
            templates.add(t2);
            ExcelExportUtils.exportXlsx(out2, templates);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
