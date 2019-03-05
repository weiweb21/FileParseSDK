package com.yanbenjun.file.parse.regist.type;

public class TypeHandleException extends RuntimeException
{
    /**
     * 
     */
    private static final long serialVersionUID = -5914172343263818526L;
    
    private String errorInfo;
    
    private String messageKey;
    
    public TypeHandleException(String errorInfo)
    {
        this.errorInfo = errorInfo;
    }

    public String getErrorInfo()
    {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo)
    {
        this.errorInfo = errorInfo;
    }

    public String getMessageKey()
    {
        return messageKey;
    }

    public void setMessageKey(String messageKey)
    {
        this.messageKey = messageKey;
    }

}
