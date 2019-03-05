package com.yanbenjun.file.parse.core;

import com.yanbenjun.file.parse.core.excel.XlsxReader;
import com.yanbenjun.file.parse.core.xml.XmlReader;

public class ReaderFactory
{
    public static Reader getReader(String filePath)
    {
        if (filePath.endsWith(".xlsx"))
        {
            return new XlsxReader();
        }
        else if (filePath.endsWith(".xml"))
        {
            return new XmlReader();
        }
        return null;
    }
}
