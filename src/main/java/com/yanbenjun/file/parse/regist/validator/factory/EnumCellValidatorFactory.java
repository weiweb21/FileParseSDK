package com.yanbenjun.file.parse.regist.validator.factory;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yanbenjun.file.parse.regist.type.EnumValidator;
import com.yanbenjun.file.parse.regist.type.SingleCellValidator;
import com.yanbenjun.file.parse.regist.type.TypeHandleException;
import com.yanbenjun.file.parse.regist.validator.EnumDataSourceFactory;

public class EnumCellValidatorFactory extends AbstractValidatorFactory {
    public EnumCellValidatorFactory(String enumExpression) {
        super(enumExpression);
    }
    
    @Override
    public SingleCellValidator newValidator() {
        EnumValidator enumValidator = new EnumValidator();
        String[] sarr = getParamExpression(validatorExpression);
        Map<String, Object> keyvalueMap = EnumDataSourceFactory.getEnumDataSource(sarr[0].trim())
                .getKeyvalueMap(sarr[1].trim());
        enumValidator.putAll(keyvalueMap);
        return enumValidator;
    }

    private String[] getParamExpression(String vstr) {
        if(StringUtils.isBlank(vstr)) {
            throw new TypeHandleException("枚举表达式异常：" + vstr);
        }
        vstr = vstr.substring("enum".length(), vstr.length());
        vstr = vstr.trim();
        vstr = vstr.substring(1, vstr.length() - 1);
        return vstr.split(":");
    }

}
