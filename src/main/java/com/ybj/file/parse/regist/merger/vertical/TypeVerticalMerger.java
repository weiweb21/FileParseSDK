package com.ybj.file.parse.regist.merger.vertical;

import com.ybj.file.parse.regist.ICanRegist;

public interface TypeVerticalMerger<T> extends ICanRegist
{
    public T merge(Object... objects);
}
