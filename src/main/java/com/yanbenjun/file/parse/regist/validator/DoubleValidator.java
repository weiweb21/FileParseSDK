package com.yanbenjun.file.parse.regist.validator;

import org.apache.commons.lang.StringUtils;

import com.yanbenjun.file.parse.regist.TypeHandleException;
import com.yanbenjun.file.parse.regist.convertor.DoubleConverter;

public class DoubleValidator implements SingleCellValidator {

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
        DoubleConverter ic = new DoubleConverter();
        try {
            ic.convert(value);
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
    public Double convert(String originalValue)
    {
        if(StringUtils.isEmpty(originalValue))
        {
            return null;
        }
        try
        {
            Double result = Double.parseDouble(originalValue.trim());
            return result;
        }
        catch (NumberFormatException e)
        {
            TypeHandleException tce = new TypeHandleException("非小数类型");
            throw tce;
        }
    }
}
