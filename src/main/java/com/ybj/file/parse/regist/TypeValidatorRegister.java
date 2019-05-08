package com.ybj.file.parse.regist;

import com.ybj.file.parse.regist.validator.DoubleValidator;
import com.ybj.file.parse.regist.validator.IntegerValidator;
import com.ybj.file.parse.regist.validator.NoneValidator;
import com.ybj.file.parse.regist.validator.NotNullValidator;
import com.ybj.file.parse.regist.validator.SingleCellValidator;

public class TypeValidatorRegister extends AbstractRegister
{
    public SingleCellValidator getTypeValidator(String typeName)
    {
        ICanRegist cr = super.getObject(typeName.trim());
        if(cr == null)
        {
            throw new RuntimeException("没有注册与类型：“" + typeName + "”对应的类型校验器");
        }
        return (SingleCellValidator)cr;
    }
    
    public static TypeValidatorRegister singleton()
    {
        return TypeValidatorRegisterBuilder.singleton;
    }
    
    private static class TypeValidatorRegisterBuilder
    {
        private static final TypeValidatorRegister singleton = new TypeValidatorRegister();
        static
        {
            singleton.regist(new NoneValidator());
            singleton.regist(new NotNullValidator());
            singleton.regist(new IntegerValidator());
            singleton.regist(new DoubleValidator());
        }
    }

}
