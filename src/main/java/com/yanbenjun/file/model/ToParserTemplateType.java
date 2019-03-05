package com.yanbenjun.file.model;

import com.yanbenjun.file.parse.regist.ICanRegist;

/**
 * 
 * @author Administrator
 *
 */
public class ToParserTemplateType implements ICanRegist
{
    private int id;
    
    private String name;
    
    private String description;
    
    public ToParserTemplateType()
    {
        
    }
    
    public ToParserTemplateType(int id, String name, String description)
    {
        this.setId(id);
        this.name = name;
        this.description = description;
    }

    public void setValue(int id)
    {
        this.setId(id);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String toString()
    {
        return this.name + ":" + this.description;
    }

    @Override
    public String getRegistKey()
    {
        return this.name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
