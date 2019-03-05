package com.yanbenjun.file.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yanbenjun.file.config.element.ColumnHead;

public class ToParseHead
{
    /** 需要解析的列 */
    private List<ColumnHead> columnHeads = new ArrayList<ColumnHead>();
    
    private Map<String, ColumnHead> headMap = new HashMap<String, ColumnHead>();
    
    public ToParseHead()
    {
    }
    
    public ToParseHead(List<ColumnHead> columnHeads)
    {
        addAll(columnHeads);
    }
    
    public ColumnHead getByFieldName(String fieldName)
    {
        return this.headMap.get(fieldName);
    }
    
    public void add(ColumnHead columnHead)
    {
        this.columnHeads.add(columnHead);
        this.headMap.put(columnHead.getFieldName(), columnHead);
    }
    
    public void addAll(List<ColumnHead> columnHeads)
    {
        this.columnHeads.addAll(columnHeads);
        for(ColumnHead ch : columnHeads)
        {
            this.headMap.put(ch.getFieldName(), ch);
        }
    }
    
    public List<ColumnHead> getColumnHeads()
    {
        return columnHeads;
    }

    public String toString()
    {
        return "需要解析的表头：" + this.columnHeads.toString();
    }
}
