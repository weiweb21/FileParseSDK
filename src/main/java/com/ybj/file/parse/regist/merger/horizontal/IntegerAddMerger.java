package com.ybj.file.parse.regist.merger.horizontal;

import java.util.stream.Stream;

import com.ybj.file.parse.regist.TypeHandleException;
import com.ybj.file.parse.regist.merger.vertical.TypeVerticalMerger;

public class IntegerAddMerger implements TypeHorizontalMerger<Integer>,TypeVerticalMerger<Integer>
{
    public static final String REGIST_KEY = "integeradd";
    @Override
    public Integer merge(Object... values) throws TypeHandleException
    {
        return Stream.of(values).filter(v -> v != null).count() == 0 ? null : Stream.of(values).filter(v -> v != null).mapToInt(v -> ((Integer)v)).sum();
    }
    
    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

}
