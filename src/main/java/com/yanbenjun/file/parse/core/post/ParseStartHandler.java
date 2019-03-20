package com.yanbenjun.file.parse.core.post;

import com.yanbenjun.file.config.element.BaseParseFileInfo;
import com.yanbenjun.file.parse.core.ParseException;
import com.yanbenjun.file.parse.core.Reader;
import com.yanbenjun.file.parse.core.ReaderFactory;
import com.yanbenjun.file.parse.core.post.infs.ParseStarter;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;

public interface ParseStartHandler extends ParseStarter, PostRowHandler
{
    public default void startParse(BaseParseFileInfo baseFileInfo) throws ParseException
    {
        Reader reader = ReaderFactory.getReader(baseFileInfo.getFileName());
        reader.read(baseFileInfo, this);
    }
}
