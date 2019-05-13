package com.ybj.file.config.element.init;

import java.util.List;

import com.ybj.file.config.ParseConfigurationException;

public class ParserBeanUtils
{
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String classOrBeanName, Class<T> clazz) {
        T instance = null;
        List<ParserBeanFactory<?>> factorys = ParserBeanFactoryRegister.getRegistedParserBeanFactorys(clazz);
        if (factorys == null) {
            throw new RuntimeException("没有找到生成" + clazz + "类型对象的工厂类注册到ParserBeanFactoryRegister");
        }
        for (ParserBeanFactory<?> factory : factorys) {
            try {
                instance= (T) factory.build(classOrBeanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if (instance == null) {
            throw new ParseConfigurationException("找不到“"+classOrBeanName+"”对应的Bean对象。");
        } else {
            return instance;
        }
    }
}
