package com.ybj.file.config.element.xml;

import com.ybj.file.config.element.BaseParseFileInfo;

/**
 * xml数据解析文件信息
 * @author Administrator
 *
 */
public class XmlParseFileInfo extends BaseParseFileInfo
{
    /**
     * 以rowTag指定的Tag作为提取行数据的基础
     * Tag下所有的xml元素作为一行数据。
     */
    private String rowTag;

    public String getRowTag()
    {
        return rowTag;
    }

    public void setRowTag(String rowTag)
    {
        this.rowTag = rowTag;
    }
}
