package com.ybj.file.config.element.init;

import com.ybj.file.config.ParseConfigurationException;
import com.ybj.file.parse.core.post.TeminationPostRowHandler;

/**
 * 类全名后置处理器生成工厂
 * @author Administrator
 *
 */
public class ClassTeminationPostRowHandlerFactory implements ParserBeanFactory<TeminationPostRowHandler>
{
    @Override
    public TeminationPostRowHandler build(String handlerStr) throws ParseConfigurationException
    {
        try
        {
            Class<?> cl = Class.forName(handlerStr);
            Class<? extends TeminationPostRowHandler> handlerClazz = cl.asSubclass(TeminationPostRowHandler.class);
            TeminationPostRowHandler teminationHandler = handlerClazz.newInstance();
            return teminationHandler;
        }
        catch (ClassCastException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            throw new ParseConfigurationException(e);
        }
    }

}
