package com.yanbenjun.file.parse.core.post.must;

import java.util.List;

import com.yanbenjun.file.config.element.ToParseTemplate;
import com.yanbenjun.file.model.parse.ColumnEntry;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.MidPostRowHandler;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.message.CellParseMessage;
import com.yanbenjun.file.parse.message.ParseContext;
import com.yanbenjun.file.parse.message.RowParseMessage;
import com.yanbenjun.file.parse.regist.type.SingleCellValidator;

/**
 * 单字段校验，用于除FileParseExtractor所有PostHandler的最前端
 * 
 * @author yanbenjun
 *
 */
public class TypeValidateHandler extends MidPostRowHandler {

    public TypeValidateHandler() {
        super();
    }

    public TypeValidateHandler(PostRowHandler next) {
        super(next);
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException {

        int sheetIndex = parsedRow.getSheetIndex();
        int rowIndex = parsedRow.getRowIndex();
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        List<ColumnEntry> cells = parsedRow.getCells();
        RowParseMessage rowParseMessage = parseContext.getCurRowMsg();
        //校验单个Cell
        for (int i = 0; i < cells.size(); i++) {
            ColumnEntry ce = cells.get(i);
            String field = ce.getField();
            String cellValue = ce.getValue();
            List<SingleCellValidator> cellValidators = toParseTemplate.getSingleCellValidator(field);
            for (SingleCellValidator validator : cellValidators) {
                if (validator.validate(cellValue)) {
                    CellParseMessage cellMsg = new CellParseMessage();
                    cellMsg.setSheetId(sheetIndex);
                    cellMsg.setRowId(rowIndex);
                    cellMsg.setColumnId(ce.getKey());
                    cellMsg.setField(field);
                    cellMsg.setTitle(ce.getTitle());
                    cellMsg.setCellOriginValue(cellValue);
                    cellMsg.setMsgType(validator.getRegistKey());
                    cellMsg.setMsg(validator.getErrorMsg());
                    rowParseMessage.add(cellMsg);
                    break;
                }
            }
        }
        
        // 校验行，如多个Cell联合主键
        
        // 往上下文中设置当前行解析信息
        next.processOne(parsedRow, parseContext);

    }

}
