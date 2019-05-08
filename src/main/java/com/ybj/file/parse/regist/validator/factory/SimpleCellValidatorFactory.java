package com.ybj.file.parse.regist.validator.factory;

import com.ybj.file.parse.regist.ICanRegist;
import com.ybj.file.parse.regist.TypeValidatorRegister;
import com.ybj.file.parse.regist.validator.SingleCellValidator;

public class SimpleCellValidatorFactory implements ValidatorFactory {

    public static final String REGIST_KEY = "simple";
    @Override
    public SingleCellValidator newValidator(String... params) {
        ICanRegist cr = TypeValidatorRegister.singleton().getObject(params[0].trim());
        if (cr == null) {
            throw new RuntimeException("没有注册与类型：“" + params[0] + "”对应的类型校验器");
        }
        return (SingleCellValidator) cr;
    }

    @Override
    public String getRegistKey() {
        return REGIST_KEY;
    }

}
