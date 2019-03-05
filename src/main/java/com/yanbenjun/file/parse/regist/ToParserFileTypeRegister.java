package com.yanbenjun.file.parse.regist;

import com.yanbenjun.file.model.ToParserTemplateType;

public class ToParserFileTypeRegister extends AbstractRegister
{
    private ToParserFileTypeRegister()
    {
        
    }
    
    public static ToParserFileTypeRegister singletonInstance()
    {
        return InnerInstanceClass.instance;
    }
    
    private static class InnerInstanceClass
    {
        private static final ToParserFileTypeRegister instance = new ToParserFileTypeRegister();
    }

    @Override
    public void setDefault()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ToParserTemplateType getDefault()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
