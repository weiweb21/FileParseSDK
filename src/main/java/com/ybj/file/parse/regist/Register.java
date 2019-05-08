package com.ybj.file.parse.regist;

public interface Register
{
    public boolean regist(ICanRegist t);

    public boolean regist(String key, ICanRegist t);
}
