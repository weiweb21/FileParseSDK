package com.yanbenjun.file.config.element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlElement(name="files")
public class Files extends XElement
{
    @XmlElement(name="toParseFile",subElement=ToParseFile.class)
    private Map<String, ToParseFile> toParseFileMap = new HashMap<String,ToParseFile>();
    
    @Override
    public void add(XElement xe)
    {
        add((ToParseFile)xe);
    }
    
    public void add(ToParseFile toParseFile)
    {
        ToParseFile tpf = toParseFileMap.putIfAbsent(toParseFile.getName(), toParseFile);
        if(tpf != null)
        {
            throw new RuntimeException("重复的全局带解析模板文件，" + tpf);
        }
    }

    public void addAll(List<ToParseFile> toParseFiles)
    {
        for(ToParseFile tpf : toParseFiles)
        {
            add(tpf);
        }
    }

    public ToParseFile get(String name)
    {
        return toParseFileMap.get(name);
    }
    
    
    public String toString()
    {
        return this.toParseFileMap.toString();
    }
}
