package com.yanbenjun.file.parse.regist.validator.factory;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import com.yanbenjun.file.parse.regist.TypeHandleException;
import com.yanbenjun.file.parse.regist.validator.EnumValidator;
import com.yanbenjun.file.parse.regist.validator.SingleCellValidator;
import com.yanbenjun.file.parse.regist.validator.datasource.EnumDataSourceFactory;

public class EnumCellValidatorFactory extends AbstractValidatorFactory
{
    public EnumCellValidatorFactory(String enumExpression)
    {
        super(enumExpression);
    }

    @Override
    public SingleCellValidator newValidator()
    {
        EnumValidator enumValidator = new EnumValidator();
        String[] params = getParamExpression(validatorExpression);
        Map<String, Object> keyvalueMap = EnumDataSourceFactory.getEnumDataSource(params).getKeyvalueMap();
        enumValidator.putAll(keyvalueMap);
        return enumValidator;
    }

    private String[] getParamExpression(String vstr)
    {
        if (StringUtils.isBlank(vstr))
        {
            throw new TypeHandleException("枚举表达式异常：" + vstr);
        }
        vstr = vstr.substring("enum".length(), vstr.length());
        vstr = vstr.trim();
        vstr = vstr.substring(1, vstr.length() - 1);
        String[] params = vstr.split(",");
        Stream.of(params).map(item -> {
            return item.trim();
        }).collect(Collectors.toList()).toArray(params);
        return params;
    }

}
