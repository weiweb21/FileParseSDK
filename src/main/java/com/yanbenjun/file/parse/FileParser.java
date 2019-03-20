package com.yanbenjun.file.parse;

import com.yanbenjun.file.config.element.BaseParseFileInfo;
import com.yanbenjun.file.parse.core.ParseException;
import com.yanbenjun.file.parse.core.post.FileParseExtractor;
import com.yanbenjun.file.parse.message.ParseContext;

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
