package com.yanbenjun.file.parse.regist.validator;

import java.util.HashMap;
import java.util.Map;

public class ExpressionEnumDataSource implements EnumDataSource {

    @Override
    public Map<String, Object> getKeyvalueMap(String expressionstr) {
        String[] kvstrArr = expressionstr.split(",");
        Map<String, Object> map = new HashMap<>();
        for (String kvstr : kvstrArr) {
            if (kvstr.contains("=")) {
                String[] kvs = kvstr.split("=");
                map.put(kvs[0], kvs[1]);
            } else {
                map.put(kvstr, kvstr);
            }
        }
        return map;
    }

}
