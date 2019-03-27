package com.yanbenjun.file.parse.regist.convertor;

import org.apache.commons.lang.StringUtils;

import com.yanbenjun.file.parse.regist.TypeHandleException;

public class DoubleConverter implements TypeConvertor<Double>
{
    public static final String REGIST_KEY = "double";
    @Override
    public Double convert(String value) throws TypeHandleException
    {
        if(StringUtils.isEmpty(value))
        {
            return null;
        }
        try
        {
            Double result = Double.parseDouble(value.trim());
            return result;
        }
        catch (NumberFormatException e)
        {
            TypeHandleException tce = new TypeHandleException("非小数类型");
            throw tce;
        }
    }
    
    public static DoubleConverter singleton()
    {
        return DoubleConverterBuilder.singleton;
    }
    
    private static class DoubleConverterBuilder
    {
        private static final DoubleConverter singleton = new DoubleConverter();
    }

    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

}
