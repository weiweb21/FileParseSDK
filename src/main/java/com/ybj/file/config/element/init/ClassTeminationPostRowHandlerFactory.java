package com.ybj.file.config.element.init;

import com.ybj.file.config.ParseConfigurationException;
import com.ybj.file.parse.core.post.TeminationPostRowHandler;

/**
 * 类全名后置处理器生成工厂
 * @author Administrator
 *
 */
public class ClassTeminationPostRowHandlerFactory implements TeminationPostRowHandlerFactory
{
    @Override
    public TeminationPostRowHandler build(String handlerStr) throws ParseConfigurationException
    {
        try
        {
            Class<?> clazz = Class.forName(handlerStr);
            Class<? extends TeminationPostRowHandler> handlerClazz = clazz.asSubclass(TeminationPostRowHandler.class);
            TeminationPostRowHandler teminationHandler = handlerClazz.newInstance();
            return teminationHandler;
        }
        catch (ClassCastException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            throw new ParseConfigurationException(e);
        }
    }

}
