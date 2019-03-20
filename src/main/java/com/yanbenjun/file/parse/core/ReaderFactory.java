package com.yanbenjun.file.parse.core;

import com.yanbenjun.file.parse.core.excel.XlsxReader;
import com.yanbenjun.file.parse.core.xml.XmlReader;

public class ReaderFactory
{

    public static Reader getReader(String fileName)
    {
        if (fileName.endsWith(".xlsx"))
        {
            return new XlsxReader();
        }
        else if (fileName.endsWith(".xml"))
        {
            return new XmlReader();
        }
        return null;
    }
}
