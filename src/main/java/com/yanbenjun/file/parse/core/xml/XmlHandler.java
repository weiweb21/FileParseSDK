package com.yanbenjun.file.parse.core.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.alibaba.fastjson.JSON;
import com.yanbenjun.file.config.element.ToParseFile;
import com.yanbenjun.file.config.element.xml.XmlPositionInfo;
import com.yanbenjun.file.model.parse.ColumnEntry;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.message.ParseContext;

/**
 * xml文件提取（提取成表格数据结构）
 * 1、提取需要知道要将哪个标签的数据提取为表格的行。
 * 2、需要知道提取的属性信息（包括属性的名称和属性对应的值），以便根据此信息，获取每一行相应属性的值
 * 3、在行标签内部，只考虑属性的名称来自于（行标签的属性名、行标签的属性名对应的值，下级标签名，下级标签对应的属性名和值）
 * 4、在行标签内部，只考虑与属性名称对应位置的属性值取值方式。
 * 5、可能有需求需要提取行标签的父级或者先祖标签内的属性数据
 * @author Administrator
 *
 */
public class XmlHandler extends DefaultHandler
{
    /**
     * 需要提取的行Tag名称
     */
    private String rowTag;
    /**
     * 
     */
    private ToParseFile toParseFile;
    /**
     * xml模板列信息取值配置（key和value的取值位置和取值匹配信息）
     */
    private List<XmlPositionInfo> xpInfos;
    private PostRowHandler startPostRowHandler;

    private ParseContext parseContext;

    /**
     * 当前标签的内容
     */
    private StringBuilder contentBuilder;
    /**
     * 当前路径
     */
    private XmlPath curXmlPath = new XmlPath();
    /**
     * 需要提取的行的正在解析当前行路径
     */
    private XmlPath rowPath = new XmlPath();

    /**
     * 缓存行
     */
    private ParsedRow parsedRow;

    /**
     * 用于存储每一行数据生成的 属性信息 每一行结束时，需要clear掉
     */
    private List<ColumnEntry> entrys = new ArrayList<>();

    /**
     * 在标签开始时未找到与key对应的value值，存入；当标签结束时，如果该valuePath=3，则去标签内部的值contentBuilder
     */
    private List<String> noneValueKeys = new ArrayList<>();

    public XmlHandler(String rowTag, ToParseFile toParseFile, PostRowHandler startPostRowHandler,
            ParseContext parseContext)
    {
        this.toParseFile = toParseFile;
        xpInfos = toParseFile.getTemplateWith(0).getToParseHead().getColumnHeads().stream().map(c -> {
            return JSON.parseObject(c.getExtendInfo(), XmlPositionInfo.class);
        }).collect(Collectors.toList());
        this.rowTag = rowTag;
        this.startPostRowHandler = startPostRowHandler;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
    {
        curXmlPath.addLast(name);
        if (name.equalsIgnoreCase(rowTag))
        {
            // 表示xml解析没有当前正在解析行，另外也可以跳过表头校验
            rowPath = new XmlPath();
            rowPath.addLast(curXmlPath);
        }

        findKey(name, attributes);
        // 更新当前标签路径
        // Clear contents cache
        contentBuilder = new StringBuilder();
    }

    /**
     * 根据该xml模板的列配置信息xpInfos，判断该标签下是否有列配置满足，满足则生成columnEntry
     * @param name
     * @param attrs
     * @return
     */
    private String findKey(String name, Attributes attrs)
    {
        String tmpKey = null;
        String tmpValue = null;
        for (XmlPositionInfo xpInfo : xpInfos)//根据该xml模板的列配置信息，判断该标签下是否有列配置满足，满足则生成columnEntry
        {
            XmlPath relativePath = xpInfo.getXmlPath();
            XmlPath absolutePath = new XmlPath();
            absolutePath.addLast(rowPath);
            absolutePath.addLast(relativePath);
            if (this.curXmlPath.equals(absolutePath))
            {
                tmpKey = xpInfo.findKey(name, attrs);
                if (tmpKey != null)
                {
                    tmpValue = xpInfo.findValue(name, attrs);
                    if (tmpValue != null)
                    {
                        entrys.add(new ColumnEntry(tmpKey, tmpValue));
                    }
                    else if(xpInfo.getValuePath() == 3)//取值位置是标签内容时，放入缓存等待标签结束时生成entry
                    {
                        noneValueKeys.add(tmpKey);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException
    {
        if (!noneValueKeys.isEmpty())
        {
            for (String tmpKey : noneValueKeys)
            {
                entrys.add(new ColumnEntry(tmpKey, contentBuilder.toString()));
            }
            noneValueKeys.clear();
        }

        if (name.equalsIgnoreCase(rowTag))
        {
            parsedRow = new ParsedRow();
            parsedRow.setRowIndex(-1);
            parsedRow.getCells().addAll(entrys);
            entrys.clear();
            compute(parsedRow, false);
        }
        // lujing -1
        curXmlPath.removeLast();
    }

    private void compute(ParsedRow parsedRow, boolean lastRow) throws RowHandleException
    {
        parsedRow.setCurTemplate(this.toParseFile.getTemplateWith(0));
        startPostRowHandler.processOne(parsedRow, parseContext);
    }

    public void endDocument() throws SAXException
    {
    }

    public void characters(char[] ch, int start, int length) throws SAXException
    {
        contentBuilder.append(new String(ch, start, length));
    }
}
