package com.ybj.file.parse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.ybj.file.config.element.BaseParseFileInfo;
import com.ybj.file.config.element.ParseSystem;
import com.ybj.file.config.element.ToParseFile;
import com.ybj.file.config.element.xml.XmlParseFileInfo;
import com.ybj.file.util.FileUtil;

public class FileParserFactory {

    public static FileParser getFileParser(String fullFilePath, Long parsePointId) {
        try {
            InputStream inputStream = new FileInputStream(fullFilePath);
            return getFileParser(fullFilePath, inputStream, parsePointId);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileParser getFileParser(String fileName, InputStream inputStream, Long parsePointId) {
        ToParseFile tpf = ParseSystem.singleton().getParsePoint(parsePointId).getToParseFileList().get(0);
        FileType type = FileType.getFileType(FileUtil.getSuffix(fileName));
        BaseParseFileInfo baseFileInfo = type.getFileInfo(fileName, inputStream, tpf);
        FileParser fp = new FileParser(baseFileInfo);
        return fp;
    }
    
    public static FileParser getFileParser(String fileName, InputStream inputStream, ToParseFile file) {
        FileType type = FileType.getFileType(FileUtil.getSuffix(fileName));
        BaseParseFileInfo baseFileInfo = type.getFileInfo(fileName, inputStream, file);
        FileParser fp = new FileParser(baseFileInfo);
        return fp;
    }

    public static enum FileType {
        XLSX("xlsx"), XLS("xls"), XML("xml") {

            @Override
            public BaseParseFileInfo getFileInfo(String fileName, InputStream inputStream, ToParseFile toParseFile) {
                XmlParseFileInfo baseFileInfo = new XmlParseFileInfo();
                baseFileInfo.setFileName(fileName);
                baseFileInfo.setInputStream(inputStream);
                baseFileInfo.setToParseFile(toParseFile);
                baseFileInfo.setRowTag(toParseFile.getRowTag());
                return baseFileInfo;
            }
        },
        TEXT("text"), DEFAULT("");

        private static Map<String, FileType> cache = new HashMap<>();

        private String type;

        private FileType(String type) {
            this.type = type;
        }

        static {
            for (FileType item : FileType.values()) {
                cache.put(item.getType(), item);
            }
        }

        public static FileType getFileType(String type) {
            return cache.get(type);
        }

        public BaseParseFileInfo getFileInfo(String fileName, InputStream inputStream, ToParseFile toParseFile) {
            BaseParseFileInfo baseFileInfo = new BaseParseFileInfo();
            baseFileInfo.setFileName(fileName);
            baseFileInfo.setInputStream(inputStream);
            baseFileInfo.setToParseFile(toParseFile);
            return baseFileInfo;
        }

        public String getType() {
            return this.type;
        }
    }
}
