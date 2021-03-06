package com.ybj.file.parse.message;

import java.util.HashMap;
import java.util.Map;

public class ParseContext {

    private Map<String, Object> context = new HashMap<>();

    private RowParseMessage curRowMsg = new RowParseMessage();

    private RowParseMessage headParseMessage = new RowParseMessage();

    private Map<Integer, String> columnFieldMap = new HashMap<>();

    public String getString(String key) {
        return (String) context.get(key);
    }

    public Integer getInt(String key) {
        return (Integer) context.get(key);
    }

    public Object getObject(String key) {
        return context.get(key);
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public void putAll(Map<String, Object> params) {
        context.putAll(params);
    }

    public void createRowMessage() {
        curRowMsg = new RowParseMessage();
    }

    public RowParseMessage getHeadParseMessage() {
        return headParseMessage;
    }

    public RowParseMessage getCurRowMsg() {
        return curRowMsg;
    }

    public Map<Integer, String> getColumnFieldMap() {
        return columnFieldMap;
    }

    public void putAllColumnFieldMap(Map<Integer, String> columnFieldMap) {
        this.columnFieldMap.putAll(columnFieldMap);
    }

    public void clearColumnFieldMap() {
        this.columnFieldMap.clear();
    }
}
