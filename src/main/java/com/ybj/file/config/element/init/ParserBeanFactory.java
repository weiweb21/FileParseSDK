package com.ybj.file.config.element.init;

import com.ybj.file.config.ParseConfigurationException;

/**
 * 实现此工厂类，可以实现对xml配置的handler不同解析方式获取终止后置处理器
 * @author Administrator
 *
 */
public interface ParserBeanFactory<T>
{
    public T build(String handlerStr) throws ParseConfigurationException;
}
