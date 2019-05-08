package com.ybj.file.parse.regist.merger.vertical;

import java.util.stream.Stream;

public class FirstNoneEmptyMerger implements TypeVerticalMerger<Object>
{
    public static final String REGIST_KEY = "firstnoneempty";

    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

    @Override
    public Object merge(Object... objects)
    {
        return Stream.of(objects).filter(obj->obj!=null).findFirst().orElse(null);
    }

}
