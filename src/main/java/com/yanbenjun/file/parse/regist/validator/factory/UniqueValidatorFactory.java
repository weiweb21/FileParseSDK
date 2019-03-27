package com.yanbenjun.file.parse.regist.validator.factory;

import com.yanbenjun.file.parse.regist.validator.UniqueValidator;

public class UniqueValidatorFactory extends AbstractValidatorFactory {
    protected UniqueValidatorFactory(String validatorExpression)
    {
        super(validatorExpression);
    }

    @Override
    public UniqueValidator newValidator()
    {
        UniqueValidator uv =  new UniqueValidator(validatorExpression);
        uv.init();
        return uv;
    }

}
