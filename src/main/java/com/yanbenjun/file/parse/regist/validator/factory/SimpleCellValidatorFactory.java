package com.yanbenjun.file.parse.regist.validator.factory;

import com.yanbenjun.file.parse.regist.ICanRegist;
import com.yanbenjun.file.parse.regist.TypeValidatorRegister;
import com.yanbenjun.file.parse.regist.type.SingleCellValidator;

public class SimpleCellValidatorFactory implements SingleCellValidatorFactory {

    @Override
    public SingleCellValidator newSingleCellValidator(String vstr) {
        ICanRegist cr = TypeValidatorRegister.singleton().getObject(vstr.trim());
        if (cr == null) {
            throw new RuntimeException("没有注册与类型：“" + vstr + "”对应的类型校验器");
        }
        return (SingleCellValidator) cr;
    }

}
