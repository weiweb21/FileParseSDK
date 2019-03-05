package com.yanbenjun.file.parse.regist.type;

import com.yanbenjun.file.parse.regist.ICanRegist;

public interface TypeValidator extends ICanRegist
{
    public String validate(Object value) throws TypeHandleException;
}
