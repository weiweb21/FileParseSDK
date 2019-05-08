package com.ybj.file.parse.core;

import com.ybj.file.config.element.BaseParseFileInfo;
import com.ybj.file.parse.core.post.ParseStartHandler;

public interface Reader {

    /**
     * 读取方法入口
     * 
     * @return
     * @throws Exception
     */
    public void read(BaseParseFileInfo baseFileInfo, ParseStartHandler startHandler) throws ParseException;
}
