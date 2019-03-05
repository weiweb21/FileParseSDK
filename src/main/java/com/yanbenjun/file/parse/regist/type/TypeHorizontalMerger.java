package com.yanbenjun.file.parse.regist.type;

import com.yanbenjun.file.parse.regist.ICanRegist;

public interface TypeHorizontalMerger<T> extends ICanRegist
{
    public T merge(Object... values) throws TypeHandleException;
}
