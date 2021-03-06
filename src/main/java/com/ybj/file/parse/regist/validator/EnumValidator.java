package com.ybj.file.parse.regist.validator;

import java.util.HashMap;
import java.util.Map;

public class EnumValidator implements SingleCellValidator {

    public static final String REGIST_KEY = "enum";
    private Map<String, Object> keyValueMap = new HashMap<>();

    private String curValue;

    public void put(String key, Object value) {
        keyValueMap.put(key, value);
    }

    public void putAll(Map<String, Object> data) {
        keyValueMap.putAll(data);
    }

    /**
     * @param 需要校验的值
     * @return 是否有错 true=有错
     */
    @Override
    public boolean validate(String value) {
        this.curValue = value;
        if (keyValueMap.containsKey(value)) {
            return false;
        }
        return true;
    }

    @Override
    public String getErrorMsg() {
        return "“" + this.curValue + "”不在有效的取值内！";
    }

    @Override
    public Object getTypeValue(String originalValue) {
        return keyValueMap.get(originalValue);
    }

    @Override
    public String getSimpleName() {
        return REGIST_KEY;
    }
}
