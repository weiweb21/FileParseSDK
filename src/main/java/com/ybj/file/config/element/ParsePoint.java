package com.ybj.file.config.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlElement(name="parsePoint")
public class ParsePoint extends XElement implements XElementAddable
{
    public ParsePoint()
    {
        
    }
    
    public ParsePoint(Long id,String mode)
    {
        this.id = id;
        this.mode = mode;
    }
    
    @XmlAttribute(name="mode")
    private String mode;
    
    @XmlElement(name="toParseFile",subElement=ToParseFile.class)
    @XmlElement(name="refFile")
    private List<ToParseFile> toParseFileList = new ArrayList<ToParseFile>();
    
    private Map<String, ToParseFile> toParseFileMap = new HashMap<>();

    @Override
    public void add(XElement xe)
    {
        add((ToParseFile)xe);
    }
    
    public void add(ToParseFile toParseFile)
    {
        toParseFileList.add(toParseFile);
        if (toParseFile.getName() != null) {
            toParseFileMap.put(toParseFile.getName(), toParseFile);
        }
    }
    
    public String toString()
    {
        return "ParsePoint{id=" + this.id + ", mode="+this.mode+", supportted file list: " + this.toParseFileList+"}";
    }

    public String getMode()
    {
        return mode;
    }

    public void setMode(String mode)
    {
        this.mode = mode;
    }

    public List<ToParseFile> getToParseFileList()
    {
        return toParseFileList;
    }
    
    public ToParseFile getToParseFile(int index)
    {
        return toParseFileList.get(index);
    }
    
    public ToParseFile getToParseFile(String name)
    {
        return toParseFileMap.get(name);
    }

    @Override
    public void clear()
    {
        toParseFileList.clear();
        toParseFileMap.clear();
    }

}
