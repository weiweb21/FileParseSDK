package com.ybj.file.parse.regist.validator.datasource;

import com.ybj.file.config.ParseConfigurationException;
import com.ybj.file.config.element.init.ParserBeanFactory;

public class DbEnumDataSourceFactory implements ParserBeanFactory<DbEnumDataSource>
{
    @Override
    public DbEnumDataSource build(String className) throws ParseConfigurationException
    {
        try
        {
            Class<?> cl = Class.forName(className);
            Class<? extends DbEnumDataSource> beanClazz = cl.asSubclass(DbEnumDataSource.class);
            DbEnumDataSource dbEnumDataSource = beanClazz.newInstance();
            return dbEnumDataSource;
        }
        catch (ClassCastException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            throw new ParseConfigurationException(e);
        }
    }

}
