package com.yanbenjun.file.parse.regist;

import com.yanbenjun.file.parse.regist.type.BooleanConvertor;
import com.yanbenjun.file.parse.regist.type.DoubleConverter;
import com.yanbenjun.file.parse.regist.type.IntegerConverter;
import com.yanbenjun.file.parse.regist.type.StringConvertor;
import com.yanbenjun.file.parse.regist.type.TypeConvertor;

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

    @Override
    public void setDefault()
    {

    }

    @Override
    public StringConvertor getDefault()
    {
        return StringConvertor.singleton();
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
