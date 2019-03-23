package com.yanbenjun.file.parse.regist.validator;

public class EnumDataSourceFactory {

    public static EnumDataSource getEnumDataSource(String dataSourceType) {
        if (dataSourceType.equals("expression")) {
            return new ExpressionEnumDataSource();
        } else {
        }
        return null;
    }
}
