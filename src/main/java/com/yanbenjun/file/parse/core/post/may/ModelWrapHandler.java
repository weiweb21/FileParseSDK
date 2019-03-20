package com.yanbenjun.file.parse.core.post.may;

import com.yanbenjun.file.config.element.ToParseFile;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.MidPostRowHandler;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.message.ParseContext;

public class ModelWrapHandler extends MidPostRowHandler
{
    public ModelWrapHandler(ToParseFile toParseFile)
    {
    }

    public ModelWrapHandler(ToParseFile toParseFile, PostRowHandler next)
    {
        super(next);
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        // TODO Auto-generated method stub
        next.processOne(parsedRow, parseContext);
    }

}
