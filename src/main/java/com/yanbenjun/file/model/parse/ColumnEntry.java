package com.yanbenjun.file.model.parse;

import com.alibaba.fastjson.JSON;

/**
 * 行数据 的 属性值信息
 * @author Administrator
 *
 */
public class ColumnEntry
{
    /**
     * xlsx,xls 第key列
     */
    private Integer key;
    
    /**
     * 数据属性名称-view使用
     */
    private String title;
    
    /**
     * 数据属性名称-Model使用
     */
    private String field;

    /**
     * 数据属性值
     */
    private String value;
    
    /**
     * 转换成Java类型 的属性值
     */
    private Object modelValue;

    public ColumnEntry(int column, String lastContents)
    {
        this.key = column;
        this.value = lastContents;
    }
    
    public ColumnEntry(String title, String lastContents)
    {
        this.title = title;
        this.value = lastContents;
    }

    public Integer getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    public String setValue(String value)
    {
        return this.value = value;
    }

    public String toString()
    {
        return JSON.toJSONString(this);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Object getModelValue()
    {
        return modelValue;
    }

    public void setModelValue(Object modelValue)
    {
        this.modelValue = modelValue;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }
}
