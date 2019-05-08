package com.ybj.file.parse.core.post.may;

import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.TeminationPostRowHandler;
import com.ybj.file.parse.message.ParseContext;

public class NormalPrinter extends TeminationPostRowHandler
{

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        if (parseContext.getCurRowMsg().isHasError())
        {
            System.out.println(parseContext.getCurRowMsg().getCellParseMsgs());
        }
        System.out.println("modelRow:" + parsedRow.getModelRow());
    }

}
