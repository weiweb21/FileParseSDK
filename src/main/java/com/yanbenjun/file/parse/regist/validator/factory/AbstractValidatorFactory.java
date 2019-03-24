package com.yanbenjun.file.parse.regist.validator.factory;

public abstract class AbstractValidatorFactory implements ValidatorFactory {
    protected String validatorExpression;
    
    protected AbstractValidatorFactory(String validatorExpression) {
        this.validatorExpression = validatorExpression;
    }

    public String getValidatorExpression()
    {
        return validatorExpression;
    }

    public void setValidatorExpression(String validatorExpression)
    {
        this.validatorExpression = validatorExpression;
    }
}
