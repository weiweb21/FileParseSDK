package com.ybj.file.parse.regist.validator;

import org.apache.commons.lang.StringUtils;

import com.ybj.file.parse.regist.ICanRegist;

public class NotNullValidator implements SingleCellValidator, ICanRegist {

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
    public Object getTypeValue(String originValue) {
        return originValue;
    }

    @Override
    public String getSimpleName() {
        return REGIST_KEY;
    }
}
