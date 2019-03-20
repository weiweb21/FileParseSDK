package com.yanbenjun.file.parse.core.post.may;

import com.yanbenjun.file.config.element.ToParseFile;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.MidPostRowHandler;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.message.ParseContext;

public class ExcludeFiltHandler extends MidPostRowHandler
{
    public ExcludeFiltHandler()
    {
        super();
    }

    public ExcludeFiltHandler(ToParseFile toParseFile, PostRowHandler next)
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
