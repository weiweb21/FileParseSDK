package com.yanbenjun.file.parse.regist.validator.datasource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExpressionEnumDataSource implements EnumDataSource {
    private String[] expressionKeyvalues;
    
    public ExpressionEnumDataSource(String[] args) {
        this.expressionKeyvalues = Arrays.copyOf(args, args.length);
    }
    @Override
    public Map<String, Object> getKeyvalueMap(String... args) {
        Map<String, Object> map = new HashMap<>();
        for (String kvstr : expressionKeyvalues) {
            if (kvstr.contains("=")) {
                String[] kvs = kvstr.split("=");
                map.put(kvs[0], kvs[1]);
            } else {
                map.put(kvstr, kvstr);
            }
        }
        return map;
    }
    public String[] getExpressionKeyvalues()
    {
        return expressionKeyvalues;
    }
    public void setExpressionKeyvalues(String[] expressionKeyvalues)
    {
        this.expressionKeyvalues = expressionKeyvalues;
    }

}
