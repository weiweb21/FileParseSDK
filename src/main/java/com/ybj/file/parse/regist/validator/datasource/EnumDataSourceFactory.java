package com.ybj.file.parse.regist.validator.datasource;

import java.util.Arrays;

public class EnumDataSourceFactory {

    public static EnumDataSource getEnumDataSource(String... enumDataSourceParams) {
        String dataSourceType = enumDataSourceParams[0];
        String[] params = Arrays.copyOfRange(enumDataSourceParams, 1,
                enumDataSourceParams.length);
        if (dataSourceType.equals("expression")) {
            return new ExpressionEnumDataSource(params);
        } else if (dataSourceType.equals("query")) {
            try {
                Class<? extends DbEnumDataSource> clazz = Class.forName(params[0]).asSubclass(DbEnumDataSource.class);
                return clazz.newInstance();
            } catch (ClassNotFoundException e) {
                //Springboot starter添加下面的代码
                /*try {
                    return (DbEnumDataSource) SpringBeanFactoryUtils.getBeanById(params[0]);
                } catch (Exception e2) {
                    throw new RuntimeException("校验器表达式错误，没有找到Bean名称为" + params[0] + "的JavaBean对象。");
                }*/
                throw new RuntimeException("校验器表达式错误，请确保类全名或者构造函数的正确。");
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | SecurityException e) {
                throw new RuntimeException("校验器表达式错误，请确保类全名或者构造函数的正确。");
            }
        }
        return null;
    }
}
