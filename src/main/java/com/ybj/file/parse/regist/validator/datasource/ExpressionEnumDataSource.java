package com.ybj.file.parse.regist.validator.datasource;

import java.util.HashMap;
import java.util.Map;

public class ExpressionEnumDataSource implements EnumDataSource {

    private String[] params;

    public ExpressionEnumDataSource(String... params) {
        this.params = params;
    }
    @Override
    public Map<String, Object> getKeyvalueMap(String... args) {
        if (params.length % 2 == 1) {
            throw new RuntimeException("静态枚举数据源表达式错误。");
        }

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < params.length; i = i + 2) {
            map.put(params[i], params[i + 1]);
        }
        return map;
    }

}
