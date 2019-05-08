package com.ybj.file.parse.regist.validator;

import org.apache.commons.lang.StringUtils;

import com.ybj.file.parse.regist.ICanRegist;
import com.ybj.file.parse.regist.TypeHandleException;
import com.ybj.file.parse.regist.convertor.DoubleConverter;

public class DoubleValidator implements SingleCellValidator, ICanRegist {

    public static final String REGIST_KEY = DoubleConverter.REGIST_KEY;
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
        try {
            getTypeValue(value);
        } catch (TypeHandleException e) {
            return true;
        }
        return false;
    }

    @Override
    public String getErrorMsg() {
        return "非数字类型";
    }

    @Override
    public Double getTypeValue(String originalValue) {
        if (StringUtils.isEmpty(originalValue)) {
            return null;
        }
        try {
            Double result = Double.parseDouble(originalValue.trim());
            return result;
        } catch (NumberFormatException e) {
            TypeHandleException tce = new TypeHandleException("非小数类型");
            throw tce;
        }
    }

    @Override
    public String getSimpleName() {
        return REGIST_KEY;
    }
}
