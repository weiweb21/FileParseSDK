package com.ybj.file.parse.regist.validator.factory;

import java.util.Arrays;
import java.util.Map;

import com.ybj.file.parse.regist.validator.EnumValidator;
import com.ybj.file.parse.regist.validator.SingleCellValidator;
import com.ybj.file.parse.regist.validator.datasource.EnumDataSourceFactory;

public class EnumCellValidatorFactory implements ValidatorFactory {

    public static final String REGIST_KEY = EnumValidator.REGIST_KEY;

    @Override
    public SingleCellValidator newValidator(String... params) {
        EnumValidator enumValidator = new EnumValidator();
        Map<String, Object> keyvalueMap = EnumDataSourceFactory
                .getEnumDataSource(Arrays.copyOfRange(params, 1, params.length)).getKeyvalueMap();
        enumValidator.putAll(keyvalueMap);
        return enumValidator;
    }

    @Override
    public String getRegistKey() {
        return REGIST_KEY;
    }

}
