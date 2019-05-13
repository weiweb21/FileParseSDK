package com.ybj.file.config.element.init;

import com.ybj.file.config.ParseConfigurationException;

public class ParserBeanUtils
{
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String classOrBeanName, Class<T> clazz) {
        T instance = null;
        for (ParserBeanFactory<?> factory : ParserBeanFactoryRegister.getRegistedParserBeanFactorys(clazz)) {
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
