package com.ybj.file.parse.regist;

import com.ybj.file.parse.regist.convertor.BooleanConvertor;
import com.ybj.file.parse.regist.convertor.DoubleConverter;
import com.ybj.file.parse.regist.convertor.IntegerConverter;
import com.ybj.file.parse.regist.convertor.StringConvertor;
import com.ybj.file.parse.regist.convertor.TypeConvertor;

public class TypeConvertorRegister extends AbstractRegister
{
    public TypeConvertor<?> getTypeConvertor(String typeName)
    {
        ICanRegist cr = super.getObject(typeName.trim());
        if(cr == null)
        {
            throw new RuntimeException("没有注册与类型：“" + typeName + "”对应的类型转换器");
        }
        return (TypeConvertor<?>)cr;
    }
    
    public static TypeConvertorRegister singleton()
    {
        return TypeConvertorRegisterBuilder.singleton;
    }
    
    private static class TypeConvertorRegisterBuilder
    {
        private static final TypeConvertorRegister singleton = new TypeConvertorRegister();
        
        static
        {
            singleton.regist(StringConvertor.singleton());
            singleton.regist(IntegerConverter.singleton());
            singleton.regist(DoubleConverter.singleton());
            singleton.regist(new BooleanConvertor());
        }
    }

}
