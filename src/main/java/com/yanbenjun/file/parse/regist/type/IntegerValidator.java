package com.yanbenjun.file.parse.regist.type;

public class IntegerValidator implements SingleCellValidator {

    public static final String REGIST_KEY = IntegerConverter.REGIST_KEY;
    @Override
    public String getRegistKey() {
        return REGIST_KEY;
    }

    /**
     * @param 需要校验的值
     * @return 是否有错 true=有错
     */
    @Override
    public boolean validate(String value) {
        IntegerConverter ic = new IntegerConverter();
        try {
            ic.convert(value);
        } catch (TypeHandleException e) {
            return true;
        }
        return false;
    }

    @Override
    public String getErrorMsg() {
        return "非整数类型";
    }
}
