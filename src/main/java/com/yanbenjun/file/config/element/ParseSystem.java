package com.yanbenjun.file.config.element;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yanbenjun.file.config.ParseConfigurationException;
import com.yanbenjun.file.util.FileUtil;

/**
 * 解析系统
 * 
 * @author Administrator
 *
 */
@XmlElement(name = "parseSystem")
public class ParseSystem extends XElement implements XElementAddable
{
    public static final String TAG_NAME = ParseSystem.class.getAnnotation(XmlElement.class).name();

    public static final Map<String, Integer> ELEMENT_SORT_MAP = new HashMap<String, Integer>();

    static
    {
        ELEMENT_SORT_MAP.put("columns", 0);
        ELEMENT_SORT_MAP.put("templates", 1);
        ELEMENT_SORT_MAP.put("files", 2);
    }

    @XmlElement(name = "parsePoint", subElement = ParsePoint.class)
    private Map<Long, ParsePoint> parsePointMap = new HashMap<>();

    @XmlElement(name = "files")
    private Files files = new Files();

    @XmlElement(name = "templates")
    private Templates templates = new Templates();

    @XmlElement(name = "columns")
    private Columns columns = new Columns();

    private static class ParseSystemBuilder
    {
        private static final ParseSystem singleton = new ParseSystem();
    }

    public static ParseSystem singleton()
    {
        return ParseSystemBuilder.singleton;
    }

    @SuppressWarnings("unchecked")
    public void load(String name, String fileName)
    {
        ParseSystem.singleton().clear();
        try
        {
            InputStream ins = FileUtil.getClassAbsolutePath(fileName);
            SAXReader reader = new SAXReader();
            Document doc;
            doc = reader.read(ins);

            Element root = doc.getRootElement();
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext())
            {
                Element parseSystemElement = iter.next();
                if (parseSystemElement.getName().equals(TAG_NAME))
                {
                    super.load(parseSystemElement);
                }
            }
        }
        catch (DocumentException | InstantiationException | IllegalAccessException e)
        {
            throw new ParseConfigurationException(e);
        }
    }

    public void addAllParsePoints(List<ParsePoint> createParsePoints)
    {
        for (ParsePoint pp : createParsePoints)
        {
            add(pp);
        }
    }

    @Override
    public void add(XElement xe)
    {
        add((ParsePoint) xe);
    }

    public void add(ParsePoint parsePoint)
    {
        ParsePoint pp = parsePointMap.putIfAbsent(parsePoint.getId(), parsePoint);
        if (pp != null)
        {
            throw new RuntimeException("重复的解析点，" + parsePoint);
        }
    }

    public void add(ToParseFile toParseFile)
    {
        files.add(toParseFile);
    }

    public void addAllFiles(List<ToParseFile> toParseFiles)
    {
        files.addAll(toParseFiles);
    }

    public void add(ToParseTemplate toParseTemplate)
    {
        templates.add(toParseTemplate);
    }

    public void addAllTemplates(List<ToParseTemplate> toParseTemplates)
    {
        templates.addAll(toParseTemplates);
    }

    public void add(ColumnHead columnHead)
    {
        columns.add(columnHead);
    }

    public void addAllColumnHeads(List<ColumnHead> columnHeads)
    {
        columns.addAll(columnHeads);
    }

    ToParseFile getToParseFile(String name)
    {
        return files.get(name);
    }

    public ToParseTemplate getToParseTemplate(String name)
    {
        return templates.get(name);
    }

    public ColumnHead getColumnHead(String id)
    {
        return columns.get(Long.parseLong(id));
    }

    public ParsePoint getParsePoint(Long id)
    {
        ParsePoint pp = this.parsePointMap.get(id);
        return pp;
    }

    public String toString()
    {
        return "{parsePoint: " + this.parsePointMap + "\n, columns: " + this.columns + "\n, templates: "
                + this.templates + "\n, files: " + this.files + "}";
    }

    @Override
    public void clear()
    {
        parsePointMap.clear();
        files.clear();
        templates.clear();
        columns.clear();
    }

}
