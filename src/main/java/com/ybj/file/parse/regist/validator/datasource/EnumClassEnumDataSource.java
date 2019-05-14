package com.ybj.file.parse.regist.validator.datasource;

import java.util.HashMap;
import java.util.Map;

import com.ybj.file.config.ParseConfigurationException;

public class EnumClassEnumDataSource implements EnumDataSource {

    private String enumclass;

    public EnumClassEnumDataSource(String enumclass) {
        this.enumclass = enumclass;
    }
    @Override
    public Map<String, Object> getKeyvalueMap(String... args) {
        try {
            Class<?> cl = Class.forName(enumclass);
            if (!cl.isEnum()) {
                throw new ParseConfigurationException(enumclass + "不是枚举类，没法通过其获取多个key-value对象。");
            }
            Object[] objects = cl.getEnumConstants();
            Map<String, Object> map = new HashMap<>();
            for (Object obj : objects) {
                EnumEntry entry = (EnumEntry) obj;
                map.put(entry.getFdKey(), entry.getFdValue());
            }
            return map;
        } catch (ClassCastException e) {
            throw new ParseConfigurationException(enumclass + "必须实现EnumEntry接口", e);
        } catch (ClassNotFoundException e) {
            throw new ParseConfigurationException("没有找到枚举类" + enumclass, e);
        }
    }

    public static interface EnumEntry {

        public String getFdKey();

        public Object getFdValue();
    }

}
