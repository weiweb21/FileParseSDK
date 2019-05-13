package com.ybj.file.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybj.file.parse.regist.validator.CacheUniqueValidator;

public class MyUniqueValidator extends CacheUniqueValidator {

    public MyUniqueValidator() {
        super();
    }

    @Override
    public List<Map<String, Object>> queryExistValues() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("ac_brand", "美的");
        return list;
    }

}
