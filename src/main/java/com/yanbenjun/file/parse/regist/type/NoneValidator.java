package com.yanbenjun.file.parse.regist.type;

public class NoneValidator implements SingleCellValidator {

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

}
