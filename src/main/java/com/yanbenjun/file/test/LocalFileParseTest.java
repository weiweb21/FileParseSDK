package com.yanbenjun.file.test;

import com.yanbenjun.file.config.ParseSystemContext;
import com.yanbenjun.file.parse.FileParser;
import com.yanbenjun.file.parse.FileParserFactory;
import com.yanbenjun.file.parse.core.ParseException;

public class LocalFileParseTest
{
    public static void main(String[] args)
    {
        System.out.println(LocalFileParseTest.class.getClassLoader());
        ParseSystemContext psc = new ParseSystemContext("fileParseContext.xml");
        FileParser fp = FileParserFactory.getFileParser("F://test.xlsx", 20190323L);
        try
        {
            fp.parse();
            fp.getContext().getCurRowMsg();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}
