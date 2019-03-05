package com.yanbenjun.file.parse.regist.type;

import com.yanbenjun.file.parse.regist.ICanRegist;

public interface TypeConvertor<T> extends ICanRegist
{
    public T convert(String value) throws TypeHandleException;
}
