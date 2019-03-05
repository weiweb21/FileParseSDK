package com.yanbenjun.file.parse.core.post;

import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;

/**
 * 文件解析行信息后处理器抽象父类
 * @author Administrator
 *
 */
public abstract class MidPostRowHandler implements PostRowHandler
{
    /**
     * 下一个行处理器
     */
    protected PostRowHandler next;
    
    public MidPostRowHandler()
    {
    }
    
    public MidPostRowHandler(PostRowHandler next)
    {
        this.next = next;
    }
    
    @Override
    public PostRowHandler next()
    {
        return next;
    }
    
    /**
     * 给当前行处理器设置下一个行处理器，并返回下一个行处理器
     */
    @Override
    public PostRowHandler next(PostRowHandler next)
    {
        this.next = next;
        return this.next;
    }
}
