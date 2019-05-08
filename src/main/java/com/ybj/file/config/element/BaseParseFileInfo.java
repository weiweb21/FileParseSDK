package com.ybj.file.config.element;

import java.io.InputStream;

import com.ybj.file.parse.message.ParseContext;

import lombok.Getter;
import lombok.Setter;

/**
 * 需要解析的的文件信息
 * 包括文件全路径、文件解析对应的文件模板：toParseFile
 * @author Administrator
 *
 */
@Getter
@Setter
public class BaseParseFileInfo
{

    private String fileName;
    
    private InputStream inputStream;

    private ParseContext parseContext = new ParseContext();
    
    private ToParseFile toParseFile;

}
