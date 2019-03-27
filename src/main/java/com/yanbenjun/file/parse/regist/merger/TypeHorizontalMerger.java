package com.yanbenjun.file.parse.regist.merger;

import com.yanbenjun.file.parse.regist.ICanRegist;
import com.yanbenjun.file.parse.regist.TypeHandleException;

public interface TypeHorizontalMerger<T> extends ICanRegist
{
    public T merge(Object... values) throws TypeHandleException;
}
