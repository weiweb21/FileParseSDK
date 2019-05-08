package com.ybj.file.parse.core.post.validate;

import java.util.List;
import java.util.stream.Collectors;

import com.ybj.file.config.element.ColumnHead;
import com.ybj.file.config.element.ToParseTemplate;
import com.ybj.file.model.parse.ColumnEntry;
import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.IllegalHeadException;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.exception.SheetBreakoutException;
import com.ybj.file.parse.core.post.MidPostRowHandler;
import com.ybj.file.parse.core.post.ParseStartHandler;
import com.ybj.file.parse.message.CellParseMessage;
import com.ybj.file.parse.message.ParseContext;
import com.ybj.file.parse.message.RowParseMessage;

/**
 * 
 * @author yanbenjun
 *
 */
@Deprecated
public class FileHeadValidator extends MidPostRowHandler implements ParseStartHandler {

    private RowParseMessage headParseMessage = new RowParseMessage();

    public FileHeadValidator() {
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException {
        RowParseMessage hpMsg = validateRow(parsedRow, parseContext);
        if (hpMsg.isHasError()) {
            headParseMessage.setHasError(true);
            headParseMessage.addAll(hpMsg.getCellParseMsgs());
            System.out.println("当前sheet页表头错误");
            throw new IllegalHeadException(hpMsg);
        }

        if (parsedRow.getCurTemplate().getHeadRow() < parsedRow.getRowIndex()) {
            System.out.println(
                    "Current sheet head validate success, skip next sheet head validate, if there is no more sheet next, it will begining to parse data.");
            throw new SheetBreakoutException();
        }
    }

    private RowParseMessage validateRow(ParsedRow dataRow, ParseContext parseContext) {
        ToParseTemplate toParseTemplate = dataRow.getCurTemplate();
        RowParseMessage headParseMessage = parseContext.getHeadParseMessage();

        if (toParseTemplate.getHeadRow() < 0) {
            headParseMessage.setRowMsg("找不到对应的表头");
            headParseMessage.setHasError(true);
            return headParseMessage;
        }
        if (toParseTemplate.getHeadRow() == dataRow.getRowIndex()) {
            List<String> keyHeads = toParseTemplate.getToParseHead().getColumnHeads().stream()
                    .filter(ColumnHead::isRequired).map(ColumnHead::getTitleName).collect(Collectors.toList());
            List<String> allDataHeads = dataRow.getCells().stream().map(ColumnEntry::getValue)
                    .collect(Collectors.toList());
            for (String keyHead : keyHeads) {
                if (!allDataHeads.contains(keyHead)) {
                    System.out.println(dataRow.getSheetIndex() + "表头：“" + keyHead + "”必须包含。");
                    CellParseMessage error = new CellParseMessage("表头：“" + keyHead + "”必须包含。", dataRow.getSheetIndex(),
                            dataRow.getRowIndex());
                    headParseMessage.add(error);
                    headParseMessage.setHasError(true);
                }
            }
        }
        return headParseMessage;
    }

}
