package com.yanbenjun.file.service;

public interface IParseService
{
    /**
     * 解析文件压缩包，压缩包里有多个文件
     * @param packagePath 压缩包路径
     * @param parsePointId 解析点，在解析点里配置有压缩包每个文件的模板
     */
    public void parsePackage(String packagePath, Long parsePointId);
    
    /**
     * 解析单个文件
     * @param filePath 文件路径
     * @param parsePointId 解析点，在解析点里配置有单个文件的模板
     */
    public void parseFile(String filePath, Long parsePointId);
}
