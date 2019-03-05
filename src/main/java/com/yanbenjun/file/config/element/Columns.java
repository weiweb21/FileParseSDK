package com.yanbenjun.file.config.element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlElement(name="columns")
public class Columns extends XElement
{
    @XmlElement(name="columnHead",subElement=ColumnHead.class)
    private Map<Long, ColumnHead> columnHeadMap = new HashMap<Long,ColumnHead>();
    
    @Override
    public void add(XElement xe)
    {
        add((ColumnHead)xe);
    }

    public void add(ColumnHead columnHead)
    {
        Long id = columnHead.getId();
        if(id == null)
        {
            throw new RuntimeException("全局columns中的columnHead缺少必须属性id");
        }
        ColumnHead last = columnHeadMap.putIfAbsent(columnHead.getId(), columnHead);
        if(last != null)
        {
            throw new RuntimeException("重复的全局带解析模板文件，" + columnHead);
        }
    }

    public void addAll(List<ColumnHead> columnHeads)
    {
        for(ColumnHead ch : columnHeads)
        {
            add(ch);
        }
    }

    public ColumnHead get(Long id)
    {
        return columnHeadMap.get(id);
    }
    
    public String toString()
    {
        return this.columnHeadMap.toString();
    }
}
