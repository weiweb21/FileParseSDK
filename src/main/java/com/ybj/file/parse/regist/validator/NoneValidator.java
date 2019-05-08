package com.ybj.file.parse.regist.validator;

import com.ybj.file.parse.regist.ICanRegist;

public class NoneValidator implements SingleCellValidator, ICanRegist {

    public static final String REGIST_KEY = "none";

    @Override
    public String getRegistKey() {
        return REGIST_KEY;
    }

    @Override
    public boolean validate(String value) {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }

    @Override
    public Object getTypeValue(String originalValue) {
        return originalValue;
    }

    @Override
    public String getSimpleName() {
        return REGIST_KEY;
    }

}
