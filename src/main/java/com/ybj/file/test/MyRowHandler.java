package com.ybj.file.test;

import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.TeminationPostRowHandler;
import com.ybj.file.parse.message.ParseContext;

public class MyRowHandler extends TeminationPostRowHandler
{

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        if (parseContext.getCurRowMsg().isHasError()) {
            System.out.println(parsedRow.getCells());
            System.out.println(parseContext.getCurRowMsg().getCellParseMsgs());
        } else {
            System.out.println(parsedRow.getModelRow());
            System.out.println(parseContext.getCurRowMsg().getCellParseMsgs());
        }
    }

}
