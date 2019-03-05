package com.yanbenjun.file.config.element.xml;

/**
 * 行属性KEY和VALUE的取值位置类型
 * xml格式的数据提取时用来标识需要提取的属性的key和value的位置
 * 0.标签
 * 1.属性名称
 * 2.属性值
 * 3.标签内容
 * 
 * @author Administrator
 *
 */
public enum PropertyKeyValueGetterType
{
    TAG(0),
    ATTR_NAME(1),
    ATTR_VALUE(2),
    TAG_VALUE(3);
    
    private int value;
    
    private PropertyKeyValueGetterType(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
