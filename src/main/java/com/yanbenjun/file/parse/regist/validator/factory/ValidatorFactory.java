package com.yanbenjun.file.parse.regist.validator.factory;

import com.yanbenjun.file.parse.regist.type.Validator;

public interface ValidatorFactory {

    public Validator newValidator();

    public static ValidatorFactory build(String vstr) {
        if (vstr.startsWith("enum")) {
            return new EnumCellValidatorFactory(vstr);
        } else if(vstr.startsWith("unique")) {
            return new UniqueValidatorFactory(vstr);
        } else {
            return new SimpleCellValidatorFactory(vstr);
        }
    }

}
