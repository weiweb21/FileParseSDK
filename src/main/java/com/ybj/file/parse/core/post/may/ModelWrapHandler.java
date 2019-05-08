package com.ybj.file.parse.core.post.may;

import com.ybj.file.config.element.ToParseFile;
import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.MidPostRowHandler;
import com.ybj.file.parse.core.post.infs.PostRowHandler;
import com.ybj.file.parse.message.ParseContext;

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
