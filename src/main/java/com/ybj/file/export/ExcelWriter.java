package com.cmiot.mng.file.export;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.cmiot.mng.file.export.templates.SheetWriteTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ExcelWriter {

    private static int dataFlushSize = 1000;

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
    public void writeWithoutHead(OutputStream outputStream, List<Map<String, Object>> dataList) {
        SheetWriteTemplate sheetWriteTemplate = new SheetWriteTemplate();
        sheetWriteTemplate.addAll(findHeaderFromDataList(dataList));
        sheetWriteTemplate.setPageDataQuery((pageNum) -> {
            return pageNum == 0 ? dataList : Collections.emptyList();
        });
        sheetWriteTemplate.setDataStartIndex(0);
        write(outputStream, Stream.of(sheetWriteTemplate).collect(Collectors.toList()));
    }

    private Map<String, String> findHeaderFromDataList(List<Map<String, Object>> dataList) {
        Map<String, String> header = new LinkedHashMap<>();
        for (Map<String, Object> data : dataList) {
            for (String entry : data.keySet()) {
                header.put(entry, entry);
            }
        }
        return header;
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
    public void write(OutputStream outputStream, Map<String, String> header,
            List<Map<String, Object>> dataList) {
        SheetWriteTemplate sheetWriteTemplate = new SheetWriteTemplate();
        sheetWriteTemplate.addAll(header);
        sheetWriteTemplate.setPageDataQuery((pageNum) -> {
            return pageNum == 0 ? dataList : Collections.emptyList();
        });
        write(outputStream, Stream.of(sheetWriteTemplate).collect(Collectors.toList()));
    }

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、默認寫到第一個Sheet頁，表頭在第一行，數據從第二行開始
     * 3、数据分批次写入Excel，每一批次的数据通过PageDataQuery查询
     * @param outputStream 輸出流
     * @param header 表頭
     * @param pageDataQuery 分頁查詢接口數據接口對象
     */
    public void write(OutputStream outputStream, Map<String, String> header, PageDataQuery pageDataQuery) {
        SheetWriteTemplate sheetWriteTemplate = new SheetWriteTemplate();
        sheetWriteTemplate.addAll(header);
        write(outputStream, Stream.of(sheetWriteTemplate).collect(Collectors.toList()));
    }

    /**
     * 1.向輸出數據流中寫入Excel數據
     * 2、多sheet頁數據寫入，根據sheetWriteTemplate中定義的格式寫入
     * 
     * @param outputStream 輸出流
     * @param sheetWriteTemplates 多sheet對應的一些配置
     */
    public void write(OutputStream outputStream, List<SheetWriteTemplate> sheetWriteTemplates) {
        try (Workbook workbook = createWorkbook(dataFlushSize)) {
            for (int i = 0; i < sheetWriteTemplates.size(); i++) {
                SheetWriteTemplate curTemplate = sheetWriteTemplates.get(i);
                Sheet curSheet = workbook.createSheet((i + 1) + "." + curTemplate.getSheetName());
                write(curSheet, curTemplate);
            }
            workbook.write(outputStream);
        } catch (IOException e) {
            log.debug("Write workbook to outputstream error.", e);
        }
    }

    /**
     * 写入表头，并且返回每一列表头的长度Map
     * 
     * @param sheet
     * @param header
     * @param cellStyle
     * @return
     */
    private Map<Integer, Integer> writeHead(Sheet sheet, Map<String, String> header, CellStyle cellStyle) {
        // 用于计算每一列的最长宽度，当结束写入sheet时，设置列宽
        Map<Integer, Integer> columnMaxLengthMap = new HashMap<>();
        Row headRow = sheet.createRow(0);
        int i = 0; // 标题列数
        for (Map.Entry<String, String> entry : header.entrySet()) {
            columnMaxLengthMap.put(i, entry.getValue().getBytes().length);
            Cell cell = headRow.createCell(i++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(entry.getValue());
        }
        return columnMaxLengthMap;
    }

    private CellStyle createCellStyleFromWorkbook(Workbook workbook) {
        // 创建单元格，并设置值表头 设置表头居中
        CellStyle styleMain = workbook.createCellStyle();
        // 水平居中
        styleMain.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        styleMain.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleMain;
    }

    private void write(Sheet sheet, SheetWriteTemplate sheetWriteTemplate) {
        Workbook workbook = sheet.getWorkbook();
        // 产生表格标题行
        sheet.setVerticallyCenter(true);
        // 创建单元格，并设置值表头 设置表头居中
        CellStyle styleMain = createCellStyleFromWorkbook(workbook);
        // 寫表頭
        Map<String, String> header = sheetWriteTemplate.getHeader();
        if (header.isEmpty()) {
            log.warn("表头为空，放弃写入Excel。");
            return;
        }

        Map<Integer, Integer> columnMaxLengthMap = writeHead(sheet, header, styleMain);

        PageDataQuery pageDataQuery = sheetWriteTemplate.getPageDataQuery();
        if (pageDataQuery == null) {
            throw new RuntimeException("缺少数据查询对象, PageDataQuery");
        }
        int pageNum = 0;
        int offset = 0;
        int curRowIndex = sheetWriteTemplate.getDataStartIndex();
        List<Map<String, Object>> dataList = null;
        do  { // 注意循環條件，寫pageDataQuery查詢方法時一定要有結束時為空的結果
            dataList = pageDataQuery.query(pageNum++);
            if (dataList != null && dataList.size() > 0) {
                for (int i=0; i<dataList.size(); i++) {
                    Map<String, Object> rowData = dataList.get(i);
                    if (curRowIndex >= getMaxSheetSize()) {// 数据超过当前sheet所能承载的极限，新建sheet存储数据
                        // 先设置上个sheet的各个列的宽度
                        setColumnSize(sheet, columnMaxLengthMap);
                        // 开始下一个sheet
                        sheet = workbook.createSheet(sheetWriteTemplate.getSheetName() + (++offset));
                        curRowIndex = sheetWriteTemplate.getDataStartIndex();
                        columnMaxLengthMap = writeHead(sheet, header, styleMain);
                    }
                    Row row = sheet.createRow(curRowIndex++);
                    int colIndex = 0;
                    for (Map.Entry<String, String> headerEntry : header.entrySet()) {
                        if (rowData.containsKey(headerEntry.getKey())) {
                            Object cellValue = rowData.get(headerEntry.getKey());
                            // 设置该列最大宽度
                            if (cellValue != null) {
                                int curRowColumnLength = cellValue.toString().getBytes().length;
                                if (columnMaxLengthMap.get(colIndex) < curRowColumnLength) {
                                    columnMaxLengthMap.put(colIndex, curRowColumnLength);
                                }
                            }

                            Cell cell = row.createCell(colIndex++);
                            cell.setCellStyle(styleMain);
                            setCellValue(cell, cellValue);
                        }
                    }
                }
            }
        } while (dataList != null && dataList.size() > 0);

        setColumnSize(sheet, columnMaxLengthMap);

    }

    /**
     * 自适应宽度(中文支持)
     * 
     * @param sheet
     * @param size
     */
    private static void setColumnSize(Sheet sheet, Map<Integer, Integer> columnMaxLengthMap) {
        for (Map.Entry<Integer, Integer> entry : columnMaxLengthMap.entrySet()) {
            sheet.setColumnWidth(entry.getKey(), entry.getValue() * 256);
        }
    }

    private void setCellValue(Cell cell, Object value) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            int intValue = (Integer) value;
            cell.setCellValue(intValue);
        } else if (value instanceof Float) {
            float fValue = (Float) value;
            cell.setCellValue(fValue);
        } else if (value instanceof Double) {
            double dValue = (Double) value;
            cell.setCellValue(dValue);
        } else if (value instanceof Long) {
            long longValue = (Long) value;
            cell.setCellValue(longValue);
        } else if (value instanceof Boolean) {
            boolean bValue = (Boolean) value;
            cell.setCellValue(bValue);
        } else if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            cell.setCellValue(sdf.format(date));
        } else {
            // 其它数据类型都当作字符串简单处理
            cell.setCellValue(value == null ? "" : value.toString());
        }
    }

    protected abstract Workbook createWorkbook(int flushSize);

    protected abstract int getMaxSheetSize();

}
