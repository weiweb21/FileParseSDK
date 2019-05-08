package com.ybj.file.parse.regist.merger.horizontal;

import com.ybj.file.parse.regist.ICanRegist;
import com.ybj.file.parse.regist.TypeHandleException;

public interface TypeHorizontalMerger<T> extends ICanRegist
{
    public T merge(Object... values) throws TypeHandleException;
}
