package com.yanbenjun.file.parse.regist;

import com.yanbenjun.file.parse.regist.convertor.StringConvertor;
import com.yanbenjun.file.parse.regist.merger.DoubleAddMerger;
import com.yanbenjun.file.parse.regist.merger.IntegerAddMerger;
import com.yanbenjun.file.parse.regist.merger.StringJoinMerger;
import com.yanbenjun.file.parse.regist.merger.TypeHorizontalMerger;

public class TypeHorizontalMergerRegister extends AbstractRegister
{
    public TypeHorizontalMerger<?> getTypeHorizontalMerger(String typeName)
    {
        ICanRegist cr = super.getObject(typeName.trim());
        if(cr == null)
        {
            throw new RuntimeException("没有注册与类型：“" + typeName + "”对应的类型转换器");
        }
        return (TypeHorizontalMerger<?>)cr;
    }

    @Override
    public void setDefault()
    {

    }

    @Override
    public StringConvertor getDefault()
    {
        return null;
    }
    
    public static TypeHorizontalMergerRegister singleton()
    {
        return TypeHorizontalMergerRegisterBuilder.singleton;
    }
    
    private static class TypeHorizontalMergerRegisterBuilder
    {
        private static final TypeHorizontalMergerRegister singleton = new TypeHorizontalMergerRegister();
        
        static
        {
            singleton.regist(new StringJoinMerger());
            singleton.regist(new IntegerAddMerger());
            singleton.regist(new DoubleAddMerger());
        }
    }

}
