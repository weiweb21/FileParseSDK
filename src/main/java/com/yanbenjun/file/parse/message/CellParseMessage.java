package com.yanbenjun.file.parse.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CellParseMessage extends Location implements IParseMessage
{
    private String msg;
    
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

    public String toString()
    {
        return "{" + super.toString() + ", msg: " + this.msg + "}";
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
