package com.yanbenjun.file.parse;

import java.util.List;

import com.yanbenjun.file.config.element.BaseParseFileInfo;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.ParseException;
import com.yanbenjun.file.parse.core.post.FileParseExtractor;
import com.yanbenjun.file.parse.core.post.validate.FileHeadValidator;
import com.yanbenjun.file.parse.message.HeadParseMessage;

public class FileParser implements Parser
{
    private BaseParseFileInfo baseFileInfo;

    public FileParser(BaseParseFileInfo baseFileInfo)
    {
        this.baseFileInfo = baseFileInfo;
    }

    @Override
    public void parse() throws ParseException
    {
        FileHeadValidator fhv = new FileHeadValidator(baseFileInfo);
        HeadParseMessage message = fhv.validate();
        if(message.isHasError()) {
            //TODO 表頭錯誤的處理,默認不處理直接返回
            System.out.println(message);
            return;
        }
        new FileParseExtractor(null).startParse(baseFileInfo);
    }

    public void parseAndPrint() throws ParseException {
        FileHeadValidator fhv = new FileHeadValidator(baseFileInfo);
        HeadParseMessage message = fhv.validate();
        if (message.isHasError()) {
            // TODO 表頭錯誤的處理,默認不處理直接返回
            System.out.println(message);
            return;
        }
    }

    public List<ParsedRow> parseXml() throws ParseException
    {
        return null;
    }

}
