package com.yanbenjun.file.config.element;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.yanbenjun.file.config.ParseConfigurationException;

public class XElement implements XElementAddable
{
    @XmlAttribute(name="id")
    protected Long id;
    
    @XmlAttribute(name="name")
    protected String name;
    
    public void add(XElement xe)
    {
        throw new UnsupportedOperationException("不支持该操作，请检查你的程序");
    }
    
    /**
     * 获取所有域名上配置了XmlElement标签的 tag-field map缓存
     * @return
     */
    public Map<String, Field> getValidSubElementMap()
    {
        Field[] fields = this.getClass().getDeclaredFields();
        Map<String,Field> validTagMap = new HashMap<String,Field>();
        for(Field field : fields)
        {
            XmlElement[] xas = field.getAnnotationsByType(XmlElement.class);
            if(xas != null)
            {
                for(XmlElement xa : xas)
                {
                    validTagMap.put(xa.name(),field);
                }
            }
        }
        return validTagMap;
    }
    
    public void setValidAttribute(Element e) throws IllegalArgumentException, IllegalAccessException
    {
        Field[] sfs = XElement.class.getDeclaredFields();
        Field[] fields = this.getClass().getDeclaredFields();
        Field[] totalFields = new Field[sfs.length + fields.length];
        System.arraycopy(sfs, 0, totalFields, 0, sfs.length);
        System.arraycopy(fields, 0, totalFields, sfs.length, fields.length);
        for(Field field : totalFields)
        {
            XmlAttribute xa = field.getAnnotation(XmlAttribute.class);
            if(xa != null)
            {
                String attrName = xa.name();
                Attribute attr = e.attribute(attrName);
                if(attr == null)
                {
                    continue;
                }
                setFieldValue(this, field, attr.getText());
            }
        }
    }
    
    private void setFieldValue(Object obj, Field field, String value) throws NumberFormatException, IllegalArgumentException, IllegalAccessException
    {
        field.setAccessible(true);
        Class<?> type = field.getType();
        if(type == String.class)
        {
            field.set(obj, value);
        }
        else if(type == Integer.class || type == int.class)
        {
            field.set(obj, Integer.valueOf(value));
        }
        else if(type == Long.class || type == long.class)
        {
            field.set(obj, Long.valueOf(value));
        }
        else if(type == Boolean.class || type == boolean.class)
        {
            field.set(obj, value.equals("true") ? true : false);
        }
        else
        {
            throw new RuntimeException("不支持：" + type.getName() +"的属性");
        }
    }
    
    /**
     * 加载Xml元素到java对象中
     * @param element
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public void load(Element element) throws InstantiationException, IllegalAccessException
    {
        setValidAttribute(element);
        Map<String,Field> validTagMap = getValidSubElementMap();
        List<Element> es = element.elements();
        es.stream().sorted((e1,e2)->{
            String n1 = e1.getName();
            Integer s1 = ParseSystem.ELEMENT_SORT_MAP.get(n1);
            String n2 = e2.getName();
            Integer s2 = ParseSystem.ELEMENT_SORT_MAP.get(n2);
            if(s1 == null)
            {
                s1 = Integer.MAX_VALUE;
            }
            if(s2 == null)
            {
                s2 = Integer.MAX_VALUE;
            }
            return s1.compareTo(s2);
        });
        Iterator<Element> iter = es.iterator();
        while(iter.hasNext())
        {
            Element subElement = iter.next();
            String tagName = subElement.getName();
            if(validTagMap.containsKey(tagName))
            {
                Field f = validTagMap.get(tagName);
                Class<?> fieldType = f.getType();
                if(XElement.class.isAssignableFrom(fieldType))
                {
                    f.setAccessible(true);
                    XElement be = (XElement) f.get(this);
                    if(be == null)
                    {
                        be = (XElement) fieldType.newInstance();
                    }
                    f.set(this, be);
                    be.load(subElement);
                }
                else
                {
                    XmlElement[] xes = f.getAnnotationsByType(XmlElement.class);
                    for(XmlElement xe : xes)
                    {
                        String name = xe.name();
                        if(name.equals(tagName))//field上可能有多个XmlElement标签
                        {
                            XElement xElement = getXElementByTagName(tagName,subElement, xe);
                            add(xElement);
                            break;
                        }
                    }
                }
            }
            else
            {
                throw new ParseConfigurationException("不支持的标签：" + tagName);
            }
        }
    }
    
    private XElement getXElementByTagName(String tagName,Element subElement, XmlElement xe) throws InstantiationException, IllegalAccessException
    {
        XElement xElement = null;
        if(tagName.equals("refColumn"))
        {
            String id = subElement.attributeValue("id");
            xElement = ParseSystem.singleton().getColumnHead(id);
        } 
        else if(tagName.equals("refTemplate"))
        {
            String name = subElement.attributeValue("name");
            String si = subElement.attributeValue("sheetIndex");
            ToParseTemplate tpt = ParseSystem.singleton().getToParseTemplate(name);
            if(StringUtils.isNotEmpty(si))
            {
                
                int sheetIndex = Integer.parseInt(subElement.attributeValue("sheetIndex"));
                tpt.setSheetIndex(sheetIndex);
            }
            xElement = tpt;
        }
        else if(tagName.equals("refFile"))
        {
            String name = subElement.attributeValue("name");
            xElement = ParseSystem.singleton().getToParseFile(name);
        }
        else
        {
            Class<?> subCls = xe.subElement();
            Object obj = subCls.newInstance();
            xElement = (XElement) obj;
            xElement.load(subElement);
        }
        return xElement;
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
