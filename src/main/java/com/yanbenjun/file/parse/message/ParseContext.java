package com.yanbenjun.file.parse.message;

import java.util.HashMap;
import java.util.Map;

public class ParseContext {

    private Map<String, Object> context = new HashMap<>();

    private RowParseMessage curRowMsg = new RowParseMessage();

    private RowParseMessage headParseMessage = new RowParseMessage();

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

    public void clearMessage() {
        curRowMsg.setHasError(false);
        curRowMsg.setRowMsg(null);
        curRowMsg.getCellParseMsgs().clear();
    }

    public RowParseMessage getHeadParseMessage() {
        return headParseMessage;
    }

    public RowParseMessage getCurRowMsg() {
        return curRowMsg;
    }
}
