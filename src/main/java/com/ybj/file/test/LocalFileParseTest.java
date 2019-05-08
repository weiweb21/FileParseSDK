package com.ybj.file.test;

import com.ybj.file.config.ParseSystemContext;
import com.ybj.file.parse.FileParser;
import com.ybj.file.parse.FileParserFactory;
import com.ybj.file.parse.core.ParseException;

public class LocalFileParseTest
{
    public static void main(String[] args)
    {
        ParseSystemContext psc = new ParseSystemContext("fileParseContext.xml");
        FileParser fp = FileParserFactory.getFileParser("D://room.xlsx", 20190326L);
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
