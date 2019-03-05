package com.yanbenjun.file.test;

import com.yanbenjun.file.model.ToParserTemplateType;

public enum BuildinFileTypes
{
    BASE(1,"普通模板","普通模板");
    
    private ToParserTemplateType type;
    
    private BuildinFileTypes(int value, String name, String description)
    {
        ToParserTemplateType tpft = new ToParserTemplateType(value,name,description);
        this.setType(tpft);
    }

    public ToParserTemplateType getType()
    {
        return type;
    }

    public void setType(ToParserTemplateType type)
    {
        this.type = type;
    }
}
