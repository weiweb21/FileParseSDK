package com.yanbenjun.file.config.element;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void add(XElement xe)
    {
        add((ToParseFile)xe);
    }
    
    public void add(ToParseFile toParseFile)
    {
        toParseFileList.add(toParseFile);
    }
    
    public void addAll(List<ToParseFile> toParseFiles)
    {
        toParseFileList.addAll(toParseFiles);
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

    public void setToParseFileList(List<ToParseFile> toParseFileList)
    {
        this.toParseFileList = toParseFileList;
    }

}
