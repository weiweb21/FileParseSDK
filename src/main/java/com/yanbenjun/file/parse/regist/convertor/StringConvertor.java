package com.yanbenjun.file.parse.regist.convertor;

import com.yanbenjun.file.parse.regist.TypeHandleException;

public class StringConvertor implements TypeConvertor<String>
{
    public static final String REGIST_KEY = "string";
    @Override
    public String convert(String value) throws TypeHandleException
    {
        return value;
    }

    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }
    
    public static StringConvertor singleton()
    {
        return StringConvertorBuilder.singleton;
    }
    
    private static class StringConvertorBuilder
    {
        private static final StringConvertor singleton = new StringConvertor();
    }

}
