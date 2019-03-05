package com.yanbenjun.file.config.element;

/**
 * 需要解析的的文件信息
 * 包括文件全路径、文件解析对应的文件模板：toParseFile
 * @author Administrator
 *
 */
public class BaseParseFileInfo
{
    protected String path;
    
    
    private ToParseFile toParseFile;
    
    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public ToParseFile getToParseFile()
    {
        return toParseFile;
    }

    public void setToParseFile(ToParseFile toParseFile)
    {
        this.toParseFile = toParseFile;
    }
}
