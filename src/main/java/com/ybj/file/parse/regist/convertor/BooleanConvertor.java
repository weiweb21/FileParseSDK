package com.ybj.file.parse.regist.convertor;

import com.ybj.file.parse.regist.TypeHandleException;

public class BooleanConvertor implements TypeConvertor<Boolean>
{
    public static final String REGIST_KEY = "boolean";
    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

    @Override
    public Boolean getTypeValue(String value) throws TypeHandleException
    {
        return "true".equals(value) || "是".equals(value) || "1".equals(value);
    }

}
