package com.ybj.file.test;

import com.ybj.file.parse.regist.validator.datasource.EnumClassEnumDataSource.EnumEntry;

public enum MyEnumType implements EnumEntry {
    YES("是", 1), NO("否", 0);
    private String fdKey;

    private Object fdValue;

    private MyEnumType(String fdKey, Object fdValue) {
        this.fdKey = fdKey;
        this.fdValue = fdValue;
    }

    @Override
    public String getFdKey() {
        // TODO Auto-generated method stub
        return this.fdKey;
    }

    @Override
    public Object getFdValue() {
        // TODO Auto-generated method stub
        return this.fdValue;
    }

}
