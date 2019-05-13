package com.ybj.file.config.element.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybj.file.parse.core.post.TeminationPostRowHandler;
import com.ybj.file.parse.regist.validator.UniqueValidator;
import com.ybj.file.parse.regist.validator.datasource.DbEnumDataSource;
import com.ybj.file.parse.regist.validator.factory.UniqueValidatorFactory;
import com.ybj.file.parse.regist.validator.datasource.ClassDbEnumDataSourceFactory;


/**
 * ParserBeanFactory的注册类
 * @author Administrator
 *
 */
public class ParserBeanFactoryRegister
{
    private static Map<Class<?>,List<ParserBeanFactory<?>>> factorysMap = new HashMap<>();
    
    static {
        factorysMap.put(TeminationPostRowHandler.class, new ArrayList<>());
        factorysMap.put(DbEnumDataSource.class, new ArrayList<>());
        factorysMap.put(UniqueValidator.class, new ArrayList<>());
        ParserBeanFactoryRegister.regist(TeminationPostRowHandler.class, new ClassTeminationPostRowHandlerFactory());
        ParserBeanFactoryRegister.regist(DbEnumDataSource.class, new ClassDbEnumDataSourceFactory());
        ParserBeanFactoryRegister.regist(UniqueValidator.class, new UniqueValidatorFactory.ClassUniqueValidatorFactory());
    }
    
    public synchronized static void regist(Class<?> clazz, ParserBeanFactory<?> parserBeanFactory) {
        List<ParserBeanFactory<?>> factorys = factorysMap.get(clazz);
        if(factorys == null) {
            factorys = new ArrayList<>();
            factorysMap.put(clazz, factorys);
        }
        factorys.add(parserBeanFactory);
    }
    
    public static List<ParserBeanFactory<?>> getRegistedParserBeanFactorys(Class<?> clazz) {
        return factorysMap.get(clazz);
    }
}
