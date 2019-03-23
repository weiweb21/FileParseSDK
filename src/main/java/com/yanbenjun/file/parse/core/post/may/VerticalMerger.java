package com.yanbenjun.file.parse.core.post.may;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.yanbenjun.file.config.element.ToParseFile;
import com.yanbenjun.file.config.element.ToParseTemplate;
import com.yanbenjun.file.model.parse.ColumnEntry;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.MidPostRowHandler;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.message.ParseContext;
import com.yanbenjun.file.parse.regist.type.TypeVerticalMerger;

public class VerticalMerger extends MidPostRowHandler
{
    private String lastKeyValue;
    private Map<String, List<Object>> lastModelRowMap = new HashMap<String, List<Object>>();

    public VerticalMerger(ToParseFile toParseFile)
    {
        super();
    }
    
    public VerticalMerger(ToParseFile toParseFile, PostRowHandler next)
    {
        super(next);
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        String primaryKey = toParseTemplate.getPrimaryKey();
        List<ColumnEntry> cells = parsedRow.getCells();
        StringBuffer sb = new StringBuffer();
        cells.stream().filter(ce->primaryKey.contains(ce.getField())).forEach(ce->sb.append(ce.getModelValue().toString()));
        String thisKeyValue = sb.toString();

        if (StringUtils.isEmpty(thisKeyValue) || StringUtils.equals(lastKeyValue, thisKeyValue) || lastModelRowMap.isEmpty())
        {
            addCurrentRow2Last(cells);
        }
        else
        {
            endModelRow(parsedRow, parseContext, thisKeyValue);
            lastKeyValue = thisKeyValue;
            addCurrentRow2Last(cells);
        }
        if(parsedRow.isLastRow())
        {
            endModelRow(parsedRow, parseContext, thisKeyValue);
            lastKeyValue = thisKeyValue;
        }
    }
    
    private void endModelRow(ParsedRow parsedRow, ParseContext parseContext, String thisKeyValue)
    {
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        Map<String, Object> modelRowMap = toParseTemplate.getFullFieldEmptyMap();
        for (Entry<String,List<Object>> entry : lastModelRowMap.entrySet())
        {
            String title = entry.getKey();
            List<Object> lastModelFieldValueList = lastModelRowMap.get(title);
            TypeVerticalMerger<?> typeVerticalMerger = toParseTemplate.getTypeVerticalMerger(title);
            Object[] values = lastModelFieldValueList.toArray();
            Object finalModelValue = typeVerticalMerger.merge(values);
            modelRowMap.put(title, finalModelValue);
            parsedRow.setModelRow(modelRowMap);
        }
        //纵向合并后，一个model行结束，清楚上一个map中的上一个model，准备存储下一个model的数据
        lastModelRowMap.clear();
        lastKeyValue = thisKeyValue;
        next.processOne(parsedRow, parseContext);
    }
    
    private void addCurrentRow2Last(List<ColumnEntry> cells)
    {
        for (int i = 0; i < cells.size(); i++)
        {
            ColumnEntry ce = cells.get(i);
            String field = ce.getField();
            if(lastModelRowMap.get(field) == null)
            {
                lastModelRowMap.put(field, new ArrayList<Object>());
            }
            lastModelRowMap.get(field).add(ce.getModelValue());
            //lastModelRowMap.computeIfAbsent(title, k -> new ArrayList<Object>()).add(ce.getModelValue());
        }
        
    }

}
