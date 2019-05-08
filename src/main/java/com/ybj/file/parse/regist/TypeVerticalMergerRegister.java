package com.ybj.file.parse.regist;

import com.ybj.file.parse.regist.merger.horizontal.DoubleAddMerger;
import com.ybj.file.parse.regist.merger.horizontal.IntegerAddMerger;
import com.ybj.file.parse.regist.merger.horizontal.StringJoinMerger;
import com.ybj.file.parse.regist.merger.vertical.FirstNoneEmptyMerger;
import com.ybj.file.parse.regist.merger.vertical.ListCollectionMerger;
import com.ybj.file.parse.regist.merger.vertical.TypeVerticalMerger;

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
