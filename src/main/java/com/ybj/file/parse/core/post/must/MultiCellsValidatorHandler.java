package com.ybj.file.parse.core.post.must;

import java.util.List;
import java.util.stream.Collectors;

import com.ybj.file.config.element.ToParseTemplate;
import com.ybj.file.model.parse.ColumnEntry;
import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.MidPostRowHandler;
import com.ybj.file.parse.core.post.infs.PostRowHandler;
import com.ybj.file.parse.message.CellParseMessage;
import com.ybj.file.parse.message.MultiCellsParseMessage;
import com.ybj.file.parse.message.ParseContext;
import com.ybj.file.parse.message.RowParseMessage;
import com.ybj.file.parse.regist.validator.MultiCellValidator;

public class MultiCellsValidatorHandler extends MidPostRowHandler {

    public MultiCellsValidatorHandler() {
        super();
    }

    public MultiCellsValidatorHandler(PostRowHandler next) {
        super(next);
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException {

        int sheetIndex = parsedRow.getSheetIndex();
        int rowIndex = parsedRow.getRowIndex();
        ToParseTemplate toParseTemplate = parsedRow.getCurTemplate();
        RowParseMessage rowParseMessage = parseContext.getCurRowMsg();
        List<String> hasErrorFields = rowParseMessage.getCellParseMsgs().stream().map(CellParseMessage::getField)
                .collect(Collectors.toList());

        // 校验行，如多个Cell联合主键
        List<MultiCellValidator> multiCellValidators = toParseTemplate.getMultiCellValidators();
        for (MultiCellValidator multiCellValidator : multiCellValidators) {
            // 联合校验的字段已经在前面有单元格错误了，则不进行校验（因为经过Convertor转换后的 值已经不是原始值）。
            if (!hasErrorFields.removeAll(multiCellValidator.getUnionKeys())) {
                if (multiCellValidator.validate(parsedRow.getModelRow())) {
                    MultiCellsParseMessage cellsMsg = new MultiCellsParseMessage();
                    cellsMsg.setSheetId(sheetIndex);
                    cellsMsg.setRowId(rowIndex);
                    List<String> unionKeys = multiCellValidator.getUnionKeys();
                    cellsMsg.setFields(unionKeys);
                    cellsMsg.setTitles(getUnionTitles(parsedRow, unionKeys));
                    cellsMsg.setCellOriginValues(getUnionOriginValues(parsedRow, unionKeys));
                    cellsMsg.setMsgType(multiCellValidator.getSimpleName());
                    cellsMsg.setMsg(multiCellValidator.getErrorMsg());
                    rowParseMessage.addMulti(cellsMsg);
                    break;
                }
            }
        }

        // 下一个处理
        next.processOne(parsedRow, parseContext);

    }

    private List<String> getUnionTitles(ParsedRow parsedRow, List<String> unionKeys) {
        return parsedRow.getCells().stream().filter(item -> {
            return unionKeys.contains(item.getField());
        }).collect(Collectors.toList()).stream().map(ColumnEntry::getTitle).collect(Collectors.toList());
    }

    private List<String> getUnionOriginValues(ParsedRow parsedRow, List<String> unionKeys) {
        return parsedRow.getCells().stream().filter(item -> {
            return unionKeys.contains(item.getField());
        }).collect(Collectors.toList()).stream().map(ColumnEntry::getValue).collect(Collectors.toList());
    }

}
