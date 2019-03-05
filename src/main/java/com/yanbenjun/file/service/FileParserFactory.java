package com.yanbenjun.file.service;

import java.util.HashMap;
import java.util.Map;

import com.yanbenjun.file.config.element.BaseParseFileInfo;
import com.yanbenjun.file.config.element.ParseSystem;
import com.yanbenjun.file.config.element.ToParseFile;
import com.yanbenjun.file.config.element.xml.XmlParseFileInfo;
import com.yanbenjun.file.parse.FileParser;
import com.yanbenjun.file.util.FileUtil;

public class FileParserFactory
{

    public static FileParser getFileParser(String filePath, Long parsePointId)
    {
        ToParseFile tpf = ParseSystem.singleton().getParsePoint(parsePointId).getToParseFileList().get(0);
        FileType type = FileType.getFileType(FileUtil.getSuffix(filePath));
        BaseParseFileInfo baseFileInfo = type.getFileInfo(filePath, tpf);
        FileParser fp = new FileParser(baseFileInfo);
        return fp;
    }

    public static enum FileType
    {
        XLSX("xlsx"), XLS("xls"), XML("xml")
        {
            @Override
            public BaseParseFileInfo getFileInfo(String filePath, ToParseFile toParseFile)
            {
                XmlParseFileInfo baseFileInfo = new XmlParseFileInfo();
                baseFileInfo.setPath(filePath);
                baseFileInfo.setToParseFile(toParseFile);
                baseFileInfo.setRowTag(toParseFile.getRowTag());
                return baseFileInfo;
            }
        },
        TEXT("text"),
        DEFAULT("");
        
        private static Map<String, FileType> cache = new HashMap<>();
        private String type;

        private FileType(String type)
        {
            this.type = type;
        }
        
        static
        {
            for(FileType item : FileType.values())
            {
                cache.put(item.getType(), item);
            }
        }
        
        public static FileType getFileType(String type)
        {
            return cache.get(type);
        }

        public BaseParseFileInfo getFileInfo(String filePath, ToParseFile toParseFile)
        {
            BaseParseFileInfo baseFileInfo = new BaseParseFileInfo();
            baseFileInfo.setPath(filePath);
            baseFileInfo.setToParseFile(toParseFile);
            return baseFileInfo;
        }

        public String getType()
        {
            return this.type;
        }
    }
}
