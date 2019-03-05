package com.yanbenjun.file.parse.core;

import com.yanbenjun.file.config.element.BaseParseFileInfo;
import com.yanbenjun.file.parse.core.post.ParseStartHandler;

public interface Reader {

    /**
     * 读取方法入口
     * 
     * @return
     * @throws Exception
     */
    public void read(BaseParseFileInfo baseFileInfo, ParseStartHandler startHandler) throws ParseException;
}
