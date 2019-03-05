package com.yanbenjun.file.parse.core.exception;

import com.yanbenjun.file.parse.message.HeadParseMessage;

/**
 * 表頭異常
 * @author Administrator
 *
 */
public class IllegalHeadException extends SheetBreakoutException
{
    private HeadParseMessage headParseMessage;
    /**
     * 
     */
    private static final long serialVersionUID = 1908245008630228139L;
    
    public IllegalHeadException()
    {
        
    }
    
    public IllegalHeadException(HeadParseMessage headParseMessage) {
        this.headParseMessage = headParseMessage;
    }
    
    public HeadParseMessage getHeadParseMessage()
    {
        return headParseMessage;
    }
    public void setHeadParseMessage(HeadParseMessage headParseMessage)
    {
        this.headParseMessage = headParseMessage;
    }

}
