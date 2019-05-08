package com.ybj.file.parse.regist.validator.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.ybj.file.parse.regist.validator.UniqueValidator;

public class UniqueValidatorFactory implements ValidatorFactory {

    public static final String REGIST_KEY = UniqueValidator.REGIST_KEY;
    @Override
    public UniqueValidator newValidator(String... params)
    {
        if (params.length > 2) {
            String clazzOrBean = params[1];
            try {
                Class<? extends UniqueValidator> clazz = Class.forName(clazzOrBean).asSubclass(UniqueValidator.class);
                List<String> unionKeys = new ArrayList<>();
                for (int i = 2; i < params.length; i++) {
                    unionKeys.add(params[i]);
                }
                return clazz.getConstructor(List.class).newInstance(unionKeys);
            } catch (ClassNotFoundException e) {
                try {
                    return null;//TODO (UniqueValidator) SpringBeanFactoryUtils.getBeanById(clazzOrBean);
                } catch (Exception e2) {
                    throw new RuntimeException("唯一校验器表达式错误，没有找到Bean名称为" + clazzOrBean + "的JavaBean对象。");
                }
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new RuntimeException("唯一校验器表达式错误，请确保类全名或者构造函数的正确。");
            }
        } else {
            throw new RuntimeException("唯一校验器表达式错误，正确的表达式形如： unique(classOrBean, key1, key2, ...)， 必须包含至少一个唯一域名称。");
        }
    }

    @Override
    public String getRegistKey() {
        return REGIST_KEY;
    }

}
