package com.ybj.file.parse.regist;

import com.ybj.file.parse.regist.validator.DoubleValidator;
import com.ybj.file.parse.regist.validator.IntegerValidator;
import com.ybj.file.parse.regist.validator.NoneValidator;
import com.ybj.file.parse.regist.validator.NotNullValidator;
import com.ybj.file.parse.regist.validator.factory.EnumCellValidatorFactory;
import com.ybj.file.parse.regist.validator.factory.SimpleCellValidatorFactory;
import com.ybj.file.parse.regist.validator.factory.UniqueValidatorFactory;
import com.ybj.file.parse.regist.validator.factory.ValidatorFactory;

public class ValidatorFactoryRegister extends AbstractRegister {

    public ValidatorFactory getValidatorFactory(String validatorType) {
        ICanRegist cr = super.getObject(validatorType.trim());
        if (cr == null) {
            throw new RuntimeException("没有注册与类型：“" + validatorType + "”对应的类型校验器工厂");
        }
        return (ValidatorFactory) cr;
    }

    public static ValidatorFactoryRegister singleton() {
        return ValidatorFactoryRegisterBuilder.singleton;
    }

    private static class ValidatorFactoryRegisterBuilder {

        private static final ValidatorFactoryRegister singleton = new ValidatorFactoryRegister();
        static {
            singleton.regist(NoneValidator.REGIST_KEY, new SimpleCellValidatorFactory());
            singleton.regist(NotNullValidator.REGIST_KEY, new SimpleCellValidatorFactory());
            singleton.regist(IntegerValidator.REGIST_KEY, new SimpleCellValidatorFactory());
            singleton.regist(DoubleValidator.REGIST_KEY, new SimpleCellValidatorFactory());
            singleton.regist(new EnumCellValidatorFactory());
            singleton.regist(new UniqueValidatorFactory());
        }
    }
}
