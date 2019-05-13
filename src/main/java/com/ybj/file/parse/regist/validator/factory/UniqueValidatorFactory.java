package com.ybj.file.parse.regist.validator.factory;

import java.util.ArrayList;
import java.util.List;

import com.ybj.file.config.ParseConfigurationException;
import com.ybj.file.config.element.init.ParserBeanFactory;
import com.ybj.file.config.element.init.ParserBeanUtils;
import com.ybj.file.parse.regist.validator.UniqueValidator;

public class UniqueValidatorFactory implements ValidatorFactory {

    public static final String REGIST_KEY = UniqueValidator.REGIST_KEY;
    @Override
    public UniqueValidator newValidator(String... params)
    {
        if (params.length > 2) {
            try {
                String clazzOrBean = params[1];
                UniqueValidator uniqueValidator = ParserBeanUtils.getBean(clazzOrBean, UniqueValidator.class);
                List<String> unionKeys = new ArrayList<>();
                for (int i = 2; i < params.length; i++) {
                    unionKeys.add(params[i]);
                }
                uniqueValidator.addAll(unionKeys);
                return uniqueValidator;
            } catch (Exception e) {
                throw new RuntimeException("唯一校验器表达式错误，请确保类全名或者构造函数的正确。", e);
            }
        } else {
            throw new RuntimeException("唯一校验器表达式错误，正确的表达式形如： unique(classOrBean, key1, key2, ...)， 必须包含至少一个唯一域名称。");
        }
    }

    @Override
    public String getRegistKey() {
        return REGIST_KEY;
    }
    
    /**
     * 使用class全名生成对象的工厂类
     * @author Administrator
     *
     */
    public static class ClassUniqueValidatorFactory implements ParserBeanFactory<UniqueValidator> {
        @Override
        public UniqueValidator build(String className) throws ParseConfigurationException
        {
            try
            {
                Class<?> cl = Class.forName(className);
                Class<? extends UniqueValidator> beanClazz = cl.asSubclass(UniqueValidator.class);
                UniqueValidator uniqueValidator = beanClazz.newInstance();
                return uniqueValidator;
            }
            catch (ClassCastException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
            {
                throw new ParseConfigurationException(e);
            }
        }
    }

}
