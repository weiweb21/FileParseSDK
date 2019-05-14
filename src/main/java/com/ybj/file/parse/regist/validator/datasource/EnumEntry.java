package com.ybj.file.parse.regist.validator.datasource;

import java.util.Map;

public class EnumEntry implements Map.Entry<String, Object> {

    private String key;

    private Object value;

    public String getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public Object setValue(Object value) {
        this.value = value;
        return this.value;
    }
}
