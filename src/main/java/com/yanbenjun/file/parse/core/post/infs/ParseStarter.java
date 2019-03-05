package com.yanbenjun.file.parse.core.post.infs;

import com.yanbenjun.file.config.element.BaseParseFileInfo;
import com.yanbenjun.file.parse.core.ParseException;

/**
 * 解析開始的地方
 * @author Administrator
 *
 */
public interface ParseStarter
{
    public void startParse(BaseParseFileInfo baseFileInfo) throws ParseException;
}
