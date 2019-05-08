package com.ybj.file.parse.core;

import com.ybj.file.parse.core.excel.XlsReader;
import com.ybj.file.parse.core.excel.XlsxReader;
import com.ybj.file.parse.core.xml.XmlReader;

public class ReaderFactory
{

    public static Reader getReader(String fileName)
    {
        if (fileName.endsWith(".xlsx"))
        {
            return new XlsxReader();
        }
        else if (fileName.endsWith(".xls")) {
            return new XlsReader();
        }
        else if (fileName.endsWith(".xml"))
        {
            return new XmlReader();
        }
        return null;
    }
}
