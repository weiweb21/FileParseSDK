package com.ybj.file.parse.regist.validator.datasource;

import java.util.Arrays;

import com.ybj.file.config.ParseConfigurationException;
import com.ybj.file.config.element.init.ParserBeanUtils;

/**
 * 枚举数据源静态工厂
 * @author Administrator
 *
 */
public class EnumDataSourceFactory {

    public static EnumDataSource getEnumDataSource(String... enumDataSourceParams) {
        String dataSourceType = enumDataSourceParams[0];
        String[] params = Arrays.copyOfRange(enumDataSourceParams, 1,
                enumDataSourceParams.length);
        if ("expression".equals(dataSourceType)) {
            return new ExpressionEnumDataSource(params);
        } else if ("enumclass".equals(dataSourceType)) {
            return new EnumClassEnumDataSource(params[0]);
        } else if ("query".equals(dataSourceType)) {
            try {
                return ParserBeanUtils.getBean(params[0], DbEnumDataSource.class);
            } catch (ParseConfigurationException e) {
                throw new RuntimeException("校验器表达式错误，请确保类全名或者构造函数的正确。");
            }
        }
        return null;
    }
}
