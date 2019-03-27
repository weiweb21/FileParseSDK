package com.yanbenjun.file.parse.regist.validator;

import org.apache.commons.lang.StringUtils;

public class NotNullValidator implements SingleCellValidator {

    public static final String REGIST_KEY = "notnull";
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
        return StringUtils.isBlank(value);
    }

    @Override
    public String getErrorMsg() {
        return "非空字段不能为空";
    }

    @Override
    public String convert(String originalValue)
    {
        return originalValue;
    }
}
