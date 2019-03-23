package com.yanbenjun.file.parse.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CellParseMessage extends Location implements IParseMessage
{
    private String msg;
    
    private String cellOriginValue;
    
    private String field;

    private String title;

    private String msgType;

    public CellParseMessage() {

    }

    public CellParseMessage(String msg)
    {
        this.msg = msg;
    }

    public CellParseMessage(String msg, int sheetId)
    {
        this(msg, sheetId, -1);
    }

    public CellParseMessage(String msg, int sheetId, int rowId)
    {
        this(msg, sheetId, rowId, -1);
    }

    public CellParseMessage(String msg, int sheetId, int rowId, int columnId)
    {
        super(sheetId, rowId, columnId);
        this.msg = msg;
    }

    @Override
    public boolean breakOut()
    {
        return false;
    }

    @Override
    public boolean isHasError() {
        return false;
    }
}
