package com.ybj.file.parse.message;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MultiCellsParseMessage extends Location implements IParseMessage
{
    private String msg;
    
    private List<String> cellOriginValues;
    
    private List<String> fields;

    private List<String> titles;

    private String msgType;

    public MultiCellsParseMessage() {

    }

    public MultiCellsParseMessage(String msg)
    {
        this.msg = msg;
    }

    public MultiCellsParseMessage(String msg, int sheetId)
    {
        this(msg, sheetId, -1);
    }

    public MultiCellsParseMessage(String msg, int sheetId, int rowId)
    {
        this(msg, sheetId, rowId, -1);
    }

    public MultiCellsParseMessage(String msg, int sheetId, int rowId, int columnId)
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
