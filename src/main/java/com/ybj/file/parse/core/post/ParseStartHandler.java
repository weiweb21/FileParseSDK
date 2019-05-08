package com.ybj.file.parse.core.post;

import com.ybj.file.config.element.BaseParseFileInfo;
import com.ybj.file.parse.core.ParseException;
import com.ybj.file.parse.core.Reader;
import com.ybj.file.parse.core.ReaderFactory;
import com.ybj.file.parse.core.post.infs.ParseStarter;
import com.ybj.file.parse.core.post.infs.PostRowHandler;

public interface ParseStartHandler extends ParseStarter, PostRowHandler
{
    public default void startParse(BaseParseFileInfo baseFileInfo) throws ParseException
    {
        Reader reader = ReaderFactory.getReader(baseFileInfo.getFileName());
        reader.read(baseFileInfo, this);
    }
}
