package com.ybj.file.parse.regist;

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
}
