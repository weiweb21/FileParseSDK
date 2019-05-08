package com.ybj.file.parse.core.post.may;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.ybj.file.config.element.ToParseTemplate;
import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.MidPostRowHandler;
import com.ybj.file.parse.core.post.infs.PostRowHandler;
import com.ybj.file.parse.message.ParseContext;

public class MapWrapHandler extends MidPostRowHandler {

    public MapWrapHandler() {
    }

    public MapWrapHandler(PostRowHandler next) {
        super(next);
    }

    /**
     * 上路对接SameTitleMergeHandler
     */
    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException {
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        Map<String, Object> modelRowMap = toParseTemplate.getFullFieldEmptyMap();
        Set<String> existHeads = parseContext.getColumnFieldMap().values().stream().collect(Collectors.toSet());
        Iterator<Entry<String, Object>> iter = modelRowMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            if (!existHeads.contains(entry.getKey())) {
                iter.remove();
            }
        }
        parsedRow.getCells().stream().forEach(ce -> modelRowMap.put(ce.getField(), ce.getModelValue()));
        parsedRow.setModelRow(modelRowMap);
        next.processOne(parsedRow, parseContext);
    }

}
