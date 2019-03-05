package com.yanbenjun.file.parse.message;

import java.util.ArrayList;
import java.util.List;

public class FileParseMessage extends ParseMessage
{
    private List<SheetParseMessage> sheetParseMessage = new ArrayList<SheetParseMessage>();

    public List<SheetParseMessage> getSheetParseMessage()
    {
        return sheetParseMessage;
    }

    public void setSheetParseMessage(List<SheetParseMessage> sheetParseMessage)
    {
        this.sheetParseMessage = sheetParseMessage;
    }

    @Override
    public boolean breakOut()
    {
        return false;
    }
}
