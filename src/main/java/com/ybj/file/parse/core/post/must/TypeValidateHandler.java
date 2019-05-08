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
import com.ybj.file.parse.regist.validator.SingleCellValidator;

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
                    cellMsg.setMsgType(validator.getSimpleName());
                    cellMsg.setMsg(validator.getErrorMsg());
                    rowParseMessage.add(cellMsg);
                    break;
                }

                // 校验通过的行，设置转换后的值
                ce.setModelValue(validator.getTypeValue(cellValue));
            }
        }
        // 下一个处理
        next.processOne(parsedRow, parseContext);

    }

}
