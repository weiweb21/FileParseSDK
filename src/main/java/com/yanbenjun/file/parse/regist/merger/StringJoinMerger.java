package com.yanbenjun.file.parse.regist.merger;

import com.yanbenjun.file.parse.regist.TypeHandleException;

public class StringJoinMerger implements TypeHorizontalMerger<String>, TypeVerticalMerger<String>
{
    public static final String REGIST_KEY = "stringjoin";
    @Override
    public String merge(Object... values) throws TypeHandleException
    {
        String result = null;
        for(Object value : values)
        {
            if(value == null)
            {
                continue;
            }
            result = result == null ? String.valueOf(value) : result + String.valueOf(value);
        }
        return result;
        
    }

    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

}
