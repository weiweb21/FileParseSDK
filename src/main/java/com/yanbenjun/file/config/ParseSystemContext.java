package com.yanbenjun.file.config;

import com.yanbenjun.file.config.element.ParseSystem;
import com.yanbenjun.file.config.element.ToParseFile;

/**
 * 模板配置上下文
 * 
 * @author Administrator
 *
 */
public class ParseSystemContext
{
    private ParseSystem parseSystem = ParseSystem.singleton();

    public ParseSystemContext(String configFile)
    {
        parseSystem.load("", configFile);
    }

    /**
     * 获取系统中指定解析点的第fileIndex个文件模板
     * 
     * @param parsePointId
     *            解析点id
     * @param fileIndex
     *            文件模板序列号
     * @return 文件模板
     */
    public ToParseFile getFileTemplate(Long parsePointId, int fileIndex)
    {
        return parseSystem.getParsePoint(parsePointId).getToParseFile(fileIndex);
    }

    /**
     * 获取系统中指定解析点的第fileIndex个文件模板
     * 
     * @param parsePointId
     *            解析点id
     * @param fileTemplateName
     *            文件模板名称
     * @return 文件模板，如果在指定解析点下找不到该名称的模板，返回空
     */
    public ToParseFile getFileTemplate(Long parsePointId, String fileTemplateName)
    {
        return parseSystem.getParsePoint(parsePointId).getToParseFile(fileTemplateName);
    }
}
