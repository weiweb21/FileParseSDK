package com.ybj.file.parse;

import com.ybj.file.config.element.BaseParseFileInfo;
import com.ybj.file.parse.core.ParseException;
import com.ybj.file.parse.core.post.FileParseExtractor;
import com.ybj.file.parse.message.ParseContext;

public class FileParser implements Parser
{
    private BaseParseFileInfo baseFileInfo;

    public FileParser(BaseParseFileInfo baseFileInfo)
    {
        this.baseFileInfo = baseFileInfo;
    }

    public ParseContext getContext() {
        return baseFileInfo.getParseContext();
    }

    @Override
    public void parse() throws ParseException
    {
        new FileParseExtractor(null).startParse(baseFileInfo);
    }

}
