package com.yanbenjun.file.parse.core.post.may;

import java.util.Map;

import com.yanbenjun.file.config.element.ToParseTemplate;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.MidPostRowHandler;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.message.ParseContext;

public class MapWrapHandler extends MidPostRowHandler
{

    public MapWrapHandler()
    {
    }
    
    public MapWrapHandler(PostRowHandler next)
    {
        super(next);
    }

    /**
     * 上路对接SameTitleMergeHandler
     */
    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        Map<String, Object> modelRowMap = toParseTemplate.getFullFieldEmptyMap();
        parsedRow.getCells().stream().forEach(ce->modelRowMap.put(ce.getField(), ce.getModelValue()));
        parsedRow.setModelRow(modelRowMap);
        next.processOne(parsedRow, parseContext);
    }

}
