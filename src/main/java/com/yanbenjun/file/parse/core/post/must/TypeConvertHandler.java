package com.yanbenjun.file.parse.core.post.must;

import java.util.List;

import com.yanbenjun.file.config.element.ToParseTemplate;
import com.yanbenjun.file.model.parse.ColumnEntry;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.MidPostRowHandler;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.message.ParseContext;
import com.yanbenjun.file.parse.regist.TypeHandleException;
import com.yanbenjun.file.parse.regist.convertor.TypeConvertor;

@Deprecated
public class TypeConvertHandler extends MidPostRowHandler
{
    public TypeConvertHandler()
    {
        super();
    }
    
    public TypeConvertHandler(PostRowHandler next)
    {
        super(next);
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException
    {
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        List<ColumnEntry> cells = parsedRow.getCells();
        for(int i=0; i< cells.size(); i++)
        {
            ColumnEntry ce = cells.get(i);
            String field = ce.getField();
            TypeConvertor<?> typeConvertor = toParseTemplate.getTypeConvertor(field);
            try
            {
                Object obj = typeConvertor.convert(ce.getValue());
                ce.setModelValue(obj);
            }
            catch (TypeHandleException e)
            {
                //TODO LOG
                /*String errorMsg = e.getErrorInfo();
                CellParseMessage cpm = new CellParseMessage(errorMsg, ce.getKey(), rowIndex, sheetIndex);
                rowParseMessage.add(cpm);
                rowParseMessage.setHasError(true);*/
            }
        }
        // 往上下文中设置当前行解析信息
        next.processOne(parsedRow, parseContext);
    }
}
