package com.ybj.file.export;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.ybj.file.export.templates.SheetWriteTemplate;

/**
 * 导出工具类
 * 方法中带xlsx的导出xlsx文件
 * 带xls的导出xls文件
 * 
 * @author yanbenjun
 *
 */
public class ExcelExportUtils {

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、默認寫到第一個Sheet頁，表頭在第一行，數據從第二行開始
     * 3、一次性写入
     * 4、表头从数据中提取，但只显示数据（只适用一次性写入所有的数据的情况）
     * 
     * @param outputStream
     *            輸出流
     * @param dataList
     *            一次性寫入的數據
     */
    public static void exportXlsxWithoutHead(OutputStream outputStream,
            List<Map<String, Object>> dataList) {
        new XlsxExcelWriter().writeWithoutHead(outputStream, dataList);
    }

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、默認寫到第一個Sheet頁，表頭在第一行，數據從第二行開始
     * 3、一次性写入
     * 4、表头从数据中提取，但只显示数据（只适用一次性写入所有的数据的情况）
     * 
     * @param outputStream
     *            輸出流
     * @param dataList
     *            一次性寫入的數據
     */
    public static void exportXlsWithoutHead(OutputStream outputStream, List<Map<String, Object>> dataList) {
        new XlsExcelWriter().writeWithoutHead(outputStream, dataList);
    }

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、默認寫到第一個Sheet頁，表頭在第一行，數據從第二行開始
     * 3、一次性写入
     * 
     * @param outputStream
     *            輸出流
     * @param header
     *            表頭
     * @param dataList
     *            一次性寫入的數據
     */
    public static void exportXlsx(OutputStream outputStream, Map<String, String> header,
            List<Map<String, Object>> dataList) {
        new XlsxExcelWriter().write(outputStream, header, dataList);
    }

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、默認寫到第一個Sheet頁，表頭在第一行，數據從第二行開始
     * 3、一次性写入
     * 
     * @param outputStream
     *            輸出流
     * @param header
     *            表頭
     * @param dataList
     *            一次性寫入的數據
     */
    public static void exportXls(OutputStream outputStream, Map<String, String> header,
            List<Map<String, Object>> dataList) {
        new XlsExcelWriter().write(outputStream, header, dataList);
    }

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、默認寫到第一個Sheet頁，表頭在第一行，數據從第二行開始
     * 3、数据分批次写入Excel，每一批次的数据通过PageDataQuery查询
     * 
     * @param outputStream
     *            輸出流
     * @param header
     *            表頭
     * @param pageDataQuery
     *            分頁查詢接口數據接口對象
     */
    public static void exportXlsx(OutputStream outputStream, Map<String, String> header, PageDataQuery pageDataQuery) {
        new XlsxExcelWriter().write(outputStream, header, pageDataQuery);
    }

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、默認寫到第一個Sheet頁，表頭在第一行，數據從第二行開始
     * 3、数据分批次写入Excel，每一批次的数据通过PageDataQuery查询
     * 
     * @param outputStream
     *            輸出流
     * @param header
     *            表頭
     * @param pageDataQuery
     *            分頁查詢接口數據接口對象
     */
    public static void exportXls(OutputStream outputStream, Map<String, String> header, PageDataQuery pageDataQuery) {
        new XlsExcelWriter().write(outputStream, header, pageDataQuery);
    }

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、多sheet頁數據寫入，根據sheetWriteTemplate中定義的格式寫入
     * 
     * @param outputStream
     *            輸出流
     * @param sheetWriteTemplates
     *            多sheet對應的一些配置
     */
    public static void exportXlsx(OutputStream outputStream, List<SheetWriteTemplate> sheetWriteTemplates) {
        new XlsxExcelWriter().write(outputStream, sheetWriteTemplates);
    }

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、多sheet頁數據寫入，根據sheetWriteTemplate中定義的格式寫入
     * 
     * @param outputStream
     *            輸出流
     * @param sheetWriteTemplates
     *            多sheet對應的一些配置
     */
    public static void exportXls(OutputStream outputStream, List<SheetWriteTemplate> sheetWriteTemplates) {
        new XlsExcelWriter().write(outputStream, sheetWriteTemplates);
    }
}
