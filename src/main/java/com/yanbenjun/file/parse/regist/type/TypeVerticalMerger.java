package com.yanbenjun.file.parse.regist.type;

import com.yanbenjun.file.parse.regist.ICanRegist;

public interface TypeVerticalMerger<T> extends ICanRegist
{
    public T merge(Object... objects);
}
