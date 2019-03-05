package com.yanbenjun.file.service;

import com.yanbenjun.file.parse.FileParser;
import com.yanbenjun.file.parse.core.ParseException;

public class ParseService implements IParseService
{

    public void parseFile(String filePath, Long parsePointId)
    {
        FileParser fp = FileParserFactory.getFileParser(filePath, parsePointId);
        try
        {
            fp.parse();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void parsePackage(String packagePath, Long parsePointId)
    {
        // TODO
    }
}
