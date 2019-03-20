package com.yanbenjun.file.parse.core.post.must;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.yanbenjun.file.config.element.ToParseTemplate;
import com.yanbenjun.file.model.parse.ColumnEntry;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.MidPostRowHandler;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.message.CellParseMessage;
import com.yanbenjun.file.parse.message.ParseContext;
import com.yanbenjun.file.parse.message.RowParseMessage;
import com.yanbenjun.file.parse.regist.type.TypeHorizontalMerger;
import com.yanbenjun.file.parse.regist.type.TypeValidator;

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
        int sheetIndex = parsedRow.getSheetIndex();
        int rowIndex = parsedRow.getRowIndex();
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        List<ColumnEntry> cells = parsedRow.getCells();
        Map<String,List<ColumnEntry>> map = new HashMap<String, List<ColumnEntry>>();
        for(int i=0; i< cells.size(); i++)
        {
            ColumnEntry ce = cells.get(i);
            if(map.get(ce.getTitle()) == null)
            {
                map.put(ce.getTitle(), new ArrayList<ColumnEntry>());
            }
            map.get(ce.getTitle()).add(ce);
        }
        
        Map<String, Object> mergeMap = new HashMap<String, Object>();
        RowParseMessage rowMsg = parseContext.getCurRowMsg();
        for(Entry<String, List<ColumnEntry>> entry : map.entrySet())
        {
            String title = entry.getKey();
            List<ColumnEntry> ces = entry.getValue();
            TypeHorizontalMerger<?> typeMerger = toParseTemplate.getTypeHorizontalMerger(title);
            List<Object> values = ces.stream().map(ColumnEntry::getModelValue).collect(Collectors.toList());
            Object obj = typeMerger.merge(values.toArray());
            TypeValidator typeValidator = toParseTemplate.getTypeValidator(title);
            String err = typeValidator.validate(obj);
            if(err != null)
            {
                System.out.println(err);
                ColumnEntry first = (ColumnEntry)entry.getValue().get(0);
                int column = first.getKey();
                rowMsg.add(new CellParseMessage(err, column, rowIndex, sheetIndex));
                // 更新行错误信息
                return;
            }
            mergeMap.put(title, obj);
        }
        
        //去重title，并将合并后的值设置进去
        Iterator<ColumnEntry> iter = cells.iterator();
        Set<String> hasSet = new HashSet<String>();
        while(iter.hasNext())
        {
            ColumnEntry ce = iter.next();
            String title = ce.getTitle();
            ce.setModelValue(mergeMap.get(title));
            if(hasSet.contains(title))
            {
                iter.remove();
            }
            else
            {
                hasSet.add(title);
            }
        }
        next.processOne(parsedRow, parseContext);
    }
}
