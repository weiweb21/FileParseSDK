package com.yanbenjun.file.test;

import java.util.HashMap;
import java.util.Map;

import com.yanbenjun.file.parse.regist.validator.datasource.DbEnumDataSource;

public class MyTestDbEnumDataSource implements DbEnumDataSource
{

    @Override
    public Map<String, Object> getKeyvalueMap(String... args)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("深圳", 1);
        map.put("广州", 2);
        map.put("珠海", 3);
        return map;
    }

}
