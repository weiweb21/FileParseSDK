package com.yanbenjun.file.config.element.xml;

import org.xml.sax.Attributes;

import com.alibaba.fastjson.JSON;
import com.yanbenjun.file.parse.core.xml.XmlPath;

public class XmlPositionInfo
{
    /**
     * 相对row路径的子路径
     */
    private XmlPath xmlPath = new XmlPath();

    /**
     * 路径方向（父目录、子目录）
     */
    private int pathDirect;

    /**
     * 属性key的位置，0表示取标签，1表示取标签的属性=attrName的属性，2表示取标签的属性=attrName且属性值=attrValue；3表示取标签内容
     * 一般来说就0,1，2有意义
     */
    private int keyPath;

    /**
     * 属性Value的位置，0表示取标签，1表示取标签的属性=attrName的属性，2表示取标签的属性=attrName的属性值；3表示取标签内容
     * 一般来说就2和3有意义
     */
    private int valuePath;

    private String attrName = "";
    private String attrValue = "";

    /**
     * 匹配当前标签是否有符合当前key位置的key，有则返回key值
     * @param tagName
     * @param attrs
     * @return
     */
    public String findKey(String tagName, Attributes attrs)
    {
        int keyPathType = keyPath;
        if (keyPathType == 0)
        {
            return tagName;
        }
        else if (keyPathType == 1)
        {
            for (int i = 0; i < attrs.getLength(); i++)
            {
                if (attrs.getQName(i).equalsIgnoreCase(attrName))
                {
                    return this.attrName;
                }
            }
        }
        else if (keyPathType == 2)
        {
            for (int i = 0; i < attrs.getLength(); i++)
            {
                if (attrs.getQName(i).equalsIgnoreCase(attrName) && attrs.getValue(i).equalsIgnoreCase(attrValue))
                {
                    return this.attrValue;
                }
            }
        }
        return null;
    }

    /**
     * 匹配当前标签下是否有符合当前值位置信息,有则返回该值
     * @param name
     * @param attrs
     * @return
     */
    public String findValue(String name, Attributes attrs)
    {
        int valuePathType = valuePath;
        if (valuePathType == 0)
        {
            return name;
        }
        else if (valuePathType == 1)
        {
            return null;
        }
        else if (valuePathType == 2)
        {
            for (int i = 0; i < attrs.getLength(); i++)
            {
                if (attrs.getQName(i).equalsIgnoreCase(attrName))
                {
                    return attrs.getValue(i);
                }
            }
        }
        return null;
    }

    public XmlPath getXmlPath()
    {
        return xmlPath;
    }

    public void setXmlPath(XmlPath xmlPath)
    {
        this.xmlPath = xmlPath;
    }

    public int getPathDirect()
    {
        return pathDirect;
    }

    public void setPathDirect(int pathDirect)
    {
        this.pathDirect = pathDirect;
    }

    public int getKeyPath()
    {
        return keyPath;
    }

    public void setKeyPath(int keyPath)
    {
        this.keyPath = keyPath;
    }

    public int getValuePath()
    {
        return valuePath;
    }

    public void setValuePath(int valuePath)
    {
        this.valuePath = valuePath;
    }

    public String getAttrName()
    {
        return attrName;
    }

    public void setAttrName(String attrName)
    {
        this.attrName = attrName;
    }

    public String getAttrValue()
    {
        return attrValue;
    }

    public void setAttrValue(String attrValue)
    {
        this.attrValue = attrValue;
    }
    
    public String toString()
    {
        return JSON.toJSONString(this);
    }
    
    public static XmlPositionInfo parse(String jsonStr)
    {
        return JSON.parseObject(jsonStr, XmlPositionInfo.class);
    }
    
    public static String getExampleJson()
    {
        XmlPositionInfo xpxc = new XmlPositionInfo();
        XmlPath xmlPath = new XmlPath();
        xmlPath.addLast("simpleData");
        xpxc.setXmlPath(xmlPath);
        xpxc.setPathDirect(1);
        xpxc.setKeyPath(2);
        xpxc.setValuePath(3);
        xpxc.setAttrName("name");
        xpxc.setAttrValue("ASDL");
        return xpxc.toString();
    }
    
    public static void main(String[] args)
    {
        String json = getExampleJson();
        System.out.println(json);
    }


}
