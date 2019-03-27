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
    
    private static EnumDataSource parse(String queryEnumParam) {
        try
        {
            Class<?> clazz =  Class.forName(queryEnumParam);
            if (DbEnumDataSource.class.isAssignableFrom(clazz)) {
                return clazz.asSubclass(DbEnumDataSource.class).newInstance();
            } else if (clazz.isEnum()) {
                return new EnumClassEnumDataSource(queryEnumParam);
            } else {
                throw new RuntimeException("枚举表达式格式异常, 找不到正确的数据库枚举数据源:" + queryEnumParam);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("枚举表达式格式异常, 找不到正确的数据库枚举数据源:" + queryEnumParam);
        }
    }
}
