package com.ybj.file.parse.regist.convertor;

import com.ybj.file.parse.regist.ICanRegist;
import com.ybj.file.parse.regist.TypeHandleException;

public interface TypeConvertor<T> extends ICanRegist
{

    public T getTypeValue(String value) throws TypeHandleException;
}
