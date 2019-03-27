package com.yanbenjun.file.parse.regist.validator.factory;

import com.yanbenjun.file.parse.regist.ICanRegist;
import com.yanbenjun.file.parse.regist.TypeValidatorRegister;
import com.yanbenjun.file.parse.regist.validator.SingleCellValidator;

public class SimpleCellValidatorFactory extends AbstractValidatorFactory {

    public SimpleCellValidatorFactory(String simpleCellValidatorExpression)
    {
        super(simpleCellValidatorExpression);
    }

    @Override
    public SingleCellValidator newValidator() {
        ICanRegist cr = TypeValidatorRegister.singleton().getObject(validatorExpression.trim());
        if (cr == null) {
            throw new RuntimeException("没有注册与类型：“" + validatorExpression + "”对应的类型校验器");
        }
        return (SingleCellValidator) cr;
    }

}
