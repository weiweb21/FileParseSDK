package com.ybj.file.parse.regist;

import com.ybj.file.parse.regist.merger.horizontal.DoubleAddMerger;
import com.ybj.file.parse.regist.merger.horizontal.IntegerAddMerger;
import com.ybj.file.parse.regist.merger.horizontal.StringJoinMerger;
import com.ybj.file.parse.regist.merger.horizontal.StringListCollectMerger;
import com.ybj.file.parse.regist.merger.horizontal.TypeHorizontalMerger;

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
            singleton.regist(new StringListCollectMerger());
        }
    }

}
