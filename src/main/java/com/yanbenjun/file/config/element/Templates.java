package com.yanbenjun.file.config.element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yanbenjun.file.config.ParseConfigurationException;

@XmlElement(name="templates")
public class Templates extends XElement
{
    @XmlElement(name = "toParseTemplate", subElement = ToParseTemplate.class)
    private Map<String, ToParseTemplate> toParseTemplateMap = new HashMap<String,ToParseTemplate>();
    
    @Override
    public void add(XElement xe)
    {
        add((ToParseTemplate)xe);
    }
    
    public void add(ToParseTemplate toParseTemplate)
    {
        String name = toParseTemplate.getName();
        if(StringUtils.isEmpty(name))
        {
            throw new ParseConfigurationException("全局sheet模板缺少必须属性name");
        }
        ToParseTemplate tpt = toParseTemplateMap.put(name,toParseTemplate);
        if(tpt != null)
        {
            throw new ParseConfigurationException("全局sheet模板包含重复名称的模板，" + toParseTemplate);
        }
    }

    public void addAll(List<ToParseTemplate> toParseTemplates)
    {
        for(ToParseTemplate tpt : toParseTemplates)
        {
            add(tpt);
        }
    }
    
    public String toString()
    {
        return this.toParseTemplateMap.toString();
    }

    public ToParseTemplate get(String name)
    {
        return toParseTemplateMap.get(name);
    }
}
