package com.yanbenjun.file.parse.core.post.infs;

import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.message.ParseContext;

/**
 * 文件解析行信息后处理器
 * @author Administrator
 *
 */
public interface PostRowHandler
{
    /**
     * 当前行处理器需要进行的处理，具体处理类实现
     * @param parsedRow
     * @param parseMessage
     * @throws RowHandleException
     */
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException;

    /**
     * 设置下一个行处理器，并返回该处理器
     * @param next 需要设置的行处理器
     * @return 行处理器
     */
    public PostRowHandler next(PostRowHandler next);
    
    /**
     * 返回下一个行处理器
     * @return
     */
    public PostRowHandler next();
}
