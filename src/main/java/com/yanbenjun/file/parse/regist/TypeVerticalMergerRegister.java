package com.yanbenjun.file.parse.regist;

import com.yanbenjun.file.parse.regist.type.DoubleAddMerger;
import com.yanbenjun.file.parse.regist.type.FirstNoneEmptyMerger;
import com.yanbenjun.file.parse.regist.type.IntegerAddMerger;
import com.yanbenjun.file.parse.regist.type.ListCollectionMerger;
import com.yanbenjun.file.parse.regist.type.StringConvertor;
import com.yanbenjun.file.parse.regist.type.StringJoinMerger;
import com.yanbenjun.file.parse.regist.type.TypeVerticalMerger;

public class TypeVerticalMergerRegister extends AbstractRegister
{
    public TypeVerticalMerger<?> getTypeVerticalMerger(String typeName)
    {
        ICanRegist cr = super.getObject(typeName.trim());
        if(cr == null)
        {
            throw new RuntimeException("没有注册与类型：“" + typeName + "”对应的类型转换器");
        }
        return (TypeVerticalMerger<?>)cr;
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
    
    public static TypeVerticalMergerRegister singleton()
    {
        return TypeVerticalMergerRegisterBuilder.singleton;
    }
    
    private static class TypeVerticalMergerRegisterBuilder
    {
        private static final TypeVerticalMergerRegister singleton = new TypeVerticalMergerRegister();
        
        static
        {
            singleton.regist(new StringJoinMerger());
            singleton.regist(new IntegerAddMerger());
            singleton.regist(new DoubleAddMerger());
            singleton.regist(new ListCollectionMerger());
            singleton.regist(new FirstNoneEmptyMerger());
        }
    }

}
