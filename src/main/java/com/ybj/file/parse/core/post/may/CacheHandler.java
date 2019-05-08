package com.ybj.file.parse.core.post.may;

import java.util.ArrayList;
import java.util.List;

import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.MidPostRowHandler;
import com.ybj.file.parse.message.ParseContext;
import com.ybj.file.parse.message.ParseMessage;

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
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        rowCache.add(parsedRow);
        messages.add(parseContext.getCurRowMsg());
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
