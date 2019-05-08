package com.ybj.file.parse.core.post.may;

import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.MidPostRowHandler;
import com.ybj.file.parse.core.post.infs.PostRowHandler;
import com.ybj.file.parse.message.ParseContext;

public class ExcludeFiltHandler extends MidPostRowHandler
{
    public ExcludeFiltHandler()
    {
        super();
    }

    public ExcludeFiltHandler(PostRowHandler next)
    {
        super(next);
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        if(parsedRow.isEmpty())
        {
            return;
        }
        next.processOne(parsedRow, parseContext);
        //TODO 具体的业务可以通过继承该类，复写该方法实现
    }

}
