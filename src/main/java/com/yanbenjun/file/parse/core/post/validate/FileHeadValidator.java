package com.yanbenjun.file.parse.core.post.validate;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.yanbenjun.file.config.element.BaseParseFileInfo;
import com.yanbenjun.file.config.element.ColumnHead;
import com.yanbenjun.file.config.element.ToParseTemplate;
import com.yanbenjun.file.model.ErrorMessage;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.ParseException;
import com.yanbenjun.file.parse.core.Reader;
import com.yanbenjun.file.parse.core.ReaderFactory;
import com.yanbenjun.file.parse.core.exception.IllegalHeadException;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.exception.SheetBreakoutException;
import com.yanbenjun.file.parse.core.post.ParseStartHandler;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.message.HeadParseMessage;
import com.yanbenjun.file.parse.message.ParseMessage;

public class FileHeadValidator implements ParseStartHandler, HeadValidator {

    private BaseParseFileInfo baseFileInfo;

    private HeadParseMessage headParseMessage = new HeadParseMessage();

    public FileHeadValidator(BaseParseFileInfo baseFileInfo) {
        this.baseFileInfo = baseFileInfo;
    }

    @Override
    public HeadParseMessage validate() throws ParseException {
        Reader reader = ReaderFactory.getReader(baseFileInfo.getPath());
        reader.read(baseFileInfo, this);
        return headParseMessage;
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseMessage parseMessage) throws RowHandleException {
        HeadParseMessage hpMsg = validateRow(parsedRow, parseMessage);
        if (hpMsg.isHasError()) {
            headParseMessage.setHasError(true);
            headParseMessage.addAll(hpMsg.getErrorMsgs());
            System.out.println("当前sheet页表头错误");
            throw new IllegalHeadException(hpMsg);
        }

        if (parsedRow.getCurTemplate().getHeadRow() < parsedRow.getRowIndex()) {
            System.out.println(
                    "Current sheet head validate success, skip next sheet head validate, if there is no more sheet next, it will begining to parse data.");
            throw new SheetBreakoutException();
        }
    }

    private HeadParseMessage validateRow(ParsedRow dataRow, ParseMessage parseMessage) {
        ToParseTemplate toParseTemplate = dataRow.getCurTemplate();
        HeadParseMessage hpMsg = new HeadParseMessage();

        if (toParseTemplate.getHeadRow() < 0) {
            ErrorMessage error = new ErrorMessage("找不到對應的表頭");
            hpMsg.add(error);
            hpMsg.setHasError(true);
            return hpMsg;
        }
        if (toParseTemplate.getHeadRow() == dataRow.getRowIndex()) {
            List<String> keyHeads = toParseTemplate.getToParseHead().getColumnHeads().stream()
                    .filter(ColumnHead::isRequired).map(ColumnHead::getTitleName).collect(Collectors.toList());
            List<String> allDataHeads = dataRow.getCells().stream().map(Entry<Integer, String>::getValue)
                    .collect(Collectors.toList());
            for (String keyHead : keyHeads) {
                if (!allDataHeads.contains(keyHead)) {
                    System.out.println(dataRow.getSheetIndex() + "表头：“" + keyHead + "”必须包含。");
                    ErrorMessage error = new ErrorMessage("表头：“" + keyHead + "”必须包含。", dataRow.getSheetIndex(),
                            dataRow.getRowIndex());
                    hpMsg.add(error);
                    hpMsg.setHasError(true);
                }
            }
        }
        return hpMsg;
    }

    /**
     * 设置下一个行处理器，并返回该处理器
     * 
     * @param next
     *            需要设置的行处理器
     * @return 行处理器
     */
    public PostRowHandler next(PostRowHandler next) {
        throw new UnsupportedOperationException("Temination PostRowHandler do not support next method.");
    }

    /**
     * 返回下一个行处理器
     * 
     * @return
     */
    public PostRowHandler next() {
        throw new UnsupportedOperationException("Temination PostRowHandler do not support next method.");
    }

}
