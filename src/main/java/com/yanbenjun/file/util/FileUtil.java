package com.yanbenjun.file.util;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil
{
    private static final String SUFFIX_PATTERN = "\\.*";
    
    public static String getSuffix(String fileName)
    {
        //May be error implements,TODO
        Pattern pattern = Pattern.compile(SUFFIX_PATTERN);
        Matcher matcher = pattern.matcher(fileName);
        if(matcher.find())
        {
            return matcher.group();
        }
        return null;
    }
    
    public static InputStream getClassAbsolutePath(String fileName)
    {
        InputStream ins = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        return ins;
    }
}
