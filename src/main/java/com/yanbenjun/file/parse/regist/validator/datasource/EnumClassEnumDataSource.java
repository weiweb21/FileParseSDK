package com.yanbenjun.file.parse.regist.validator.datasource;

import java.util.HashMap;
import java.util.Map;

public class EnumClassEnumDataSource implements EnumDataSource
{
    private String clazzName;
    
    public EnumClassEnumDataSource(String clazzName) {
        this.clazzName = clazzName;
    }

    @Override
    public Map<String, Object> getKeyvalueMap(String... args)
    {
        try
        {
            Map<String, Object> map = new HashMap<>();
            Class<?> clazz = Class.forName(clazzName);
            if(clazz.isEnum() && ViewModelMapper.class.isAssignableFrom(clazz)) {
                for(Object item : clazz.getEnumConstants()){
                    ViewModelMapper mapper = (ViewModelMapper) item;
                    map.put(mapper.getKey(), mapper.getValue());
                }
                return map;
            } else {
                throw new RuntimeException("枚举源类型不对,必须为实现ViewModelMapper抽象接口的枚举类.");
            }
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("枚举源类型不对,必须为实现ViewModelMapper抽象接口的枚举类.");
        }
    }
}
