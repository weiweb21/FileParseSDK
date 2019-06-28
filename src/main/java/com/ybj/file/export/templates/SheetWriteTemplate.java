package com.cmiot.mng.file.export.templates;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cmiot.mng.file.export.PageDataQuery;

public class SheetWriteTemplate {

    /**
     * sheet名稱
     */
    private String sheetName = "Data";
    
    /**
     * sheet頁碼
     */
    private int sheetIndex = 0;

    /**
     * 數據起始行
     */
    private int dataStartIndex = 1;

    /**
     * 表頭
     */
    private Map<String, String> header = new LinkedHashMap<>();

    /**
     * 當前sheet數據獲取接口
     */
    private PageDataQuery pageDataQuery;

    public SheetWriteTemplate() {
    }

    public SheetWriteTemplate(PageDataQuery pageDataQuery) {
        this.pageDataQuery = pageDataQuery;
    }

    public SheetWriteTemplate(PageDataQuery pageDataQuery, Map<String, String> header) {
        this.pageDataQuery = pageDataQuery;
        this.header.putAll(header);
    }

    public void add(String key, String value) {
        header.put(key, value);
    }

    public void addAll(Map<String, String> map) {
        header.putAll(map);
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public PageDataQuery getPageDataQuery() {
        return pageDataQuery;
    }

    public void setPageDataQuery(PageDataQuery pageDataQuery) {
        this.pageDataQuery = pageDataQuery;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public int getDataStartIndex() {
        return dataStartIndex;
    }

    public void setDataStartIndex(int dataStartIndex) {
        this.dataStartIndex = dataStartIndex;
    }

}
