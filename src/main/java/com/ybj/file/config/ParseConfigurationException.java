package com.ybj.file.config;

/**
 * 解析系统配置错误异常
 * @author Administrator
 *
 */
public class ParseConfigurationException extends RuntimeException
{
    /**
     * 
     */
    private static final long serialVersionUID = -1003556679131123127L;
    
    public ParseConfigurationException()
    {
        super();
    }
    
    public ParseConfigurationException(String message)
    {
        super(message);
    }
    
    public ParseConfigurationException(Throwable e)
    {
        super(e);
    }

    public ParseConfigurationException(String error, Throwable e) {
        super(error, e);
    }

}
