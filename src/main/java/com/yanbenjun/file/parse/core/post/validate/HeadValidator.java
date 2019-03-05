package com.yanbenjun.file.parse.core.post.validate;

import com.yanbenjun.file.parse.core.ParseException;
import com.yanbenjun.file.parse.message.HeadParseMessage;

public interface HeadValidator
{
    public HeadParseMessage validate() throws ParseException;
}
