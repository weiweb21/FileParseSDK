package com.yanbenjun.file.parse.regist;

public interface Register
{
    public boolean regist(ICanRegist t);
    
    public void setDefault();
    
    public ICanRegist getDefault();
}
