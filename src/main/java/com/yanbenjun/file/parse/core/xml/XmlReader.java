package com.yanbenjun.file.parse.core.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.yanbenjun.file.config.element.BaseParseFileInfo;
import com.yanbenjun.file.config.element.xml.XmlParseFileInfo;
import com.yanbenjun.file.parse.core.ParseException;
import com.yanbenjun.file.parse.core.Reader;
import com.yanbenjun.file.parse.core.post.ParseStartHandler;

public class XmlReader implements Reader
{
    @Override
    public void read(BaseParseFileInfo baseFileInfo, ParseStartHandler startHandler) throws ParseException
    {
        try
        {
            XmlParseFileInfo xmlFileInfo = (XmlParseFileInfo) baseFileInfo;

            SAXParserFactory sParserFactory = SAXParserFactory.newInstance();
            SAXParser parser = sParserFactory.newSAXParser();
            String tagName = xmlFileInfo.getRowTag();
            XmlHandler handler = new XmlHandler(tagName, xmlFileInfo.getToParseFile(), startHandler,
                    xmlFileInfo.getParseContext());
            parser.parse(xmlFileInfo.getInputStream(), handler);
        }
        catch (ParserConfigurationException | SAXException |IOException e)
        {
            throw new ParseException(e);
        }
    }

}
