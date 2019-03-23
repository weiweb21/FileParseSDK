package com.yanbenjun.file.test;

import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.TeminationPostRowHandler;
import com.yanbenjun.file.parse.message.ParseContext;

public class MyRowHandler extends TeminationPostRowHandler
{

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        System.out.println(parsedRow.getModelRow());
        System.out.println(parseContext.getCurRowMsg().getCellParseMsgs());
    }

}
