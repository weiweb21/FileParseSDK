package com.yanbenjun.file.parse.core.post;

import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;

public abstract class TeminationPostRowHandler implements PostRowHandler
{
    /**
     * 设置下一个行处理器，并返回该处理器
     * @param next 需要设置的行处理器
     * @return 行处理器
     */
    public PostRowHandler next(PostRowHandler next)
    {
        throw new UnsupportedOperationException("Temination PostRowHandler do not support next method.");
    }
    
    /**
     * 返回下一个行处理器
     * @return
     */
    public PostRowHandler next()
    {
        throw new UnsupportedOperationException("Temination PostRowHandler do not support next method.");
    }
}
