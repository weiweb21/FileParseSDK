package com.yanbenjun.file.parse.regist.validator;

import org.apache.commons.lang.StringUtils;

import com.yanbenjun.file.parse.regist.TypeHandleException;
import com.yanbenjun.file.parse.regist.convertor.IntegerConverter;
import com.yanbenjun.file.util.NumberUtils;

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
        try {
            convert(value);
        } catch (TypeHandleException e) {
            return true;
        }
        return false;
    }

    @Override
    public String getErrorMsg() {
        return "非整数类型";
    }

    @Override
    public Integer convert(String originalValue)
    {

        if(StringUtils.isEmpty(originalValue))
        {
            return null;
        }
        try
        {
            Integer result = Integer.parseInt(NumberUtils.trimZeroAndDot(originalValue.trim()));
            return result;
        }
        catch (NumberFormatException e)
        {
            TypeHandleException tce = new TypeHandleException("非整数类型");
            throw tce;
        }
    
    }
}
