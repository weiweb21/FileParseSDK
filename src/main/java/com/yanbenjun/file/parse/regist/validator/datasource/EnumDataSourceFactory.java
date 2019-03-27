package com.yanbenjun.file.parse.regist.validator.datasource;

import java.util.Arrays;

public class EnumDataSourceFactory {

    public static EnumDataSource getEnumDataSource(String... args) {
        String dataSourceType = args[0];
        String[] params = Arrays.copyOfRange(args, 1, args.length);
        if (dataSourceType.equals("expression")) {
            return new ExpressionEnumDataSource(params);
        } else if (dataSourceType.equals("query")){
            return parse(params[0]);
        }
        return null;
    }
    
    private static DbEnumDataSource parse(String queryEnumParam) {
        try
        {
            @SuppressWarnings("unchecked")
            Class<? extends DbEnumDataSource> clazz = (Class<? extends DbEnumDataSource>) Class.forName(queryEnumParam);
            return clazz.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("枚举表达式格式异常, 找不到正确的数据库枚举数据源:" + queryEnumParam);
        }
    }
}
