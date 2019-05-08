package com.ybj.file.parse.core.post.infs;

import com.ybj.file.config.element.BaseParseFileInfo;
import com.ybj.file.parse.core.ParseException;

/**
 * 解析開始的地方
 * @author Administrator
 *
 */
public interface ParseStarter
{
    public void startParse(BaseParseFileInfo baseFileInfo) throws ParseException;
}
