package com.yanbenjun.file.parse.regist.convertor;

import com.yanbenjun.file.parse.regist.ICanRegist;
import com.yanbenjun.file.parse.regist.TypeHandleException;

public interface TypeConvertor<T> extends ICanRegist
{
    public T convert(String value) throws TypeHandleException;
}
