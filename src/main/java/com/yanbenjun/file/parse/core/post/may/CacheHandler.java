package com.yanbenjun.file.parse.core.post.may;

import java.util.ArrayList;
import java.util.List;

import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.MidPostRowHandler;
import com.yanbenjun.file.parse.message.ParseMessage;

/**
 * 缓存行数据
 * @author Administrator
 *
 */
public class CacheHandler extends MidPostRowHandler
{
    private List<ParsedRow> rowCache = new ArrayList<>();
    private List<ParseMessage> messages = new ArrayList<>();
    @Override
    public void processOne(ParsedRow parsedRow, ParseMessage parseMessage) throws RowHandleException
    {
        rowCache.add(parsedRow);
        messages.add(parseMessage);
    }
    
    public List<ParsedRow> getRowCache()
    {
        return rowCache;
    }
    
    public List<ParseMessage> getMessages()
    {
        return messages;
    }

}
