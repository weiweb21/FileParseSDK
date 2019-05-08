package com.ybj.file.parse.core.exception;

import com.ybj.file.parse.message.RowParseMessage;

/**
 * 表頭異常
 * @author Administrator
 *
 */
public class IllegalHeadException extends SheetBreakoutException
{

    private RowParseMessage headParseMessage;
    /**
     * 
     */
    private static final long serialVersionUID = 1908245008630228139L;
    
    public IllegalHeadException()
    {
        
    }
    
    public IllegalHeadException(RowParseMessage headParseMessage) {
        this.headParseMessage = headParseMessage;
    }
    
    public RowParseMessage getRowParseMessage()
    {
        return headParseMessage;
    }

    public void setRowParseMessage(RowParseMessage headParseMessage)
    {
        this.headParseMessage = headParseMessage;
    }

}
