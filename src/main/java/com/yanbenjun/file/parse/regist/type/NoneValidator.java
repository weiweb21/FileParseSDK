package com.yanbenjun.file.parse.regist.type;

public class NoneValidator implements TypeValidator
{
    public static final String REGIST_KEY = "none";
    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

    @Override
    public String validate(Object value) throws TypeHandleException
    {
        return null;
    }

}
