package com.ybj.file.parse.core.post.must;

import java.util.List;

import com.ybj.file.config.element.ToParseTemplate;
import com.ybj.file.model.parse.ColumnEntry;
import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.MidPostRowHandler;
import com.ybj.file.parse.core.post.infs.PostRowHandler;
import com.ybj.file.parse.message.CellParseMessage;
import com.ybj.file.parse.message.ParseContext;
import com.ybj.file.parse.message.RowParseMessage;
import com.ybj.file.parse.regist.TypeHandleException;
import com.ybj.file.parse.regist.convertor.TypeConvertor;

/**
 * 
 * @author yanbenjun
 *         功能整合到TypeValidateHandler里面，此类作废
 */
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
        int sheetIndex = parsedRow.getSheetIndex();
        int rowIndex = parsedRow.getRowIndex();
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        List<ColumnEntry> cells = parsedRow.getCells();
        RowParseMessage rowParseMessage = parseContext.getCurRowMsg();
        for(int i=0; i< cells.size(); i++)
        {
            ColumnEntry ce = cells.get(i);
            String field = ce.getField();
            TypeConvertor<?> typeConvertor = toParseTemplate.getTypeConvertor(field);
            try
            {
                Object obj = typeConvertor.getTypeValue(ce.getValue());
                ce.setModelValue(obj);
            }
            catch (TypeHandleException e)
            {
                String errorMsg = e.getErrorInfo();
                CellParseMessage cpm = new CellParseMessage(errorMsg, ce.getKey(), rowIndex, sheetIndex);
                cpm.setCellOriginValue(ce.getValue());
                rowParseMessage.add(cpm);
                rowParseMessage.setHasError(true);
            }
        }
        // 往上下文中设置当前行解析信息
        next.processOne(parsedRow, parseContext);
    }
}
