package com.yanbenjun.file.parse.regist.merger;

import java.util.Arrays;
import java.util.List;

public class ListCollectionMerger implements TypeVerticalMerger<List<Object>>
{

    public static final String REGIST_KEY = "listCollect";
    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

    @Override
    public List<Object> merge(Object... objects)
    {
        return Arrays.asList(objects);
    }

}
