package com.yanbenjun.file.parse.regist.validator.factory;

import com.yanbenjun.file.parse.regist.type.UniqueValidator;

public class UniqueValidatorFactory extends AbstractValidatorFactory {
    protected UniqueValidatorFactory(String validatorExpression)
    {
        super(validatorExpression);
    }

    @Override
    public UniqueValidator newValidator()
    {
        UniqueValidator uv =  new UniqueValidator();
        uv.init();
        return uv;
    }

}
