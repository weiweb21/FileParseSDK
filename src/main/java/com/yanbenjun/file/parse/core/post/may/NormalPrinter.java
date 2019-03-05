package com.yanbenjun.file.parse.core.post.may;

import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.TeminationPostRowHandler;
import com.yanbenjun.file.parse.message.ParseMessage;
import com.yanbenjun.file.parse.message.RowParseMessage;

public class NormalPrinter extends TeminationPostRowHandler
{

    @Override
    public void processOne(ParsedRow parsedRow, ParseMessage parseMessage) throws RowHandleException
    {
        //System.out.println("sheetIndex:" + parsedRow.getSheetIndex());
        //System.out.println("rowIndex:" + parsedRow.getRowIndex());
        if(parseMessage.isHasError())
        {
            System.out.println(((RowParseMessage)parseMessage).getCellParseMsgs().get(0).getMsg());
        }
        System.out.println("modelRow:" + parsedRow.getModelRow());
    }

}
