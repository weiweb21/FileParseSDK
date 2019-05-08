package com.ybj.file.parse.core.post.must;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.ybj.file.config.element.ToParseTemplate;
import com.ybj.file.model.parse.ColumnEntry;
import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.MidPostRowHandler;
import com.ybj.file.parse.core.post.infs.PostRowHandler;
import com.ybj.file.parse.message.ParseContext;
import com.ybj.file.parse.regist.merger.horizontal.TypeHorizontalMerger;

/**
 * 相同列关键词（表头）的内容合并处理器
 * @author Administrator
 *
 */
public class SameTitleMergeHandler extends MidPostRowHandler
{
    public SameTitleMergeHandler()
    {
        super();
    }
    
    public SameTitleMergeHandler(PostRowHandler next)
    {
        super(next);
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        List<ColumnEntry> cells = parsedRow.getCells();
        Map<String,List<ColumnEntry>> map = new HashMap<String, List<ColumnEntry>>();
        for(int i=0; i< cells.size(); i++)
        {
            ColumnEntry ce = cells.get(i);
            if(map.get(ce.getField()) == null)
            {
                map.put(ce.getField(), new ArrayList<ColumnEntry>());
            }
            map.get(ce.getField()).add(ce);
        }
        
        Map<String, Object> mergeMap = new HashMap<String, Object>();
        for(Entry<String, List<ColumnEntry>> entry : map.entrySet())
        {
            String field = entry.getKey();
            List<ColumnEntry> ces = entry.getValue();
            TypeHorizontalMerger<?> typeMerger = toParseTemplate.getTypeHorizontalMerger(field);
            List<Object> values = ces.stream().map(ColumnEntry::getModelValue).collect(Collectors.toList());
            Object obj = typeMerger.merge(values.toArray());
            mergeMap.put(field, obj);
        }
        
        //去重title，并将合并后的值设置进去
        Iterator<ColumnEntry> iter = cells.iterator();
        Set<String> hasSet = new HashSet<String>();
        while(iter.hasNext())
        {
            ColumnEntry ce = iter.next();
            String field = ce.getField();
            ce.setModelValue(mergeMap.get(field));
            if(hasSet.contains(field))
            {
                iter.remove();
            }
            else
            {
                hasSet.add(field);
            }
        }
        next.processOne(parsedRow, parseContext);
    }
}
