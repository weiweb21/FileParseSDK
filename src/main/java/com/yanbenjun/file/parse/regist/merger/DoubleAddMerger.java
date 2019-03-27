package com.yanbenjun.file.parse.regist.merger;

import java.util.stream.Stream;

import com.yanbenjun.file.parse.regist.TypeHandleException;

public class DoubleAddMerger implements TypeHorizontalMerger<Double>, TypeVerticalMerger<Double>
{
    public static final String REGIST_KEY = "doubleadd";
    @Override
    public Double merge(Object... values) throws TypeHandleException
    {
        Stream<Object> s = Stream.of(values).filter(v -> v != null);
        return s.count() == 0 ? null : Stream.of(values).filter(v -> v != null).mapToDouble(v -> ((Double)v)).sum();
    }
    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

}
