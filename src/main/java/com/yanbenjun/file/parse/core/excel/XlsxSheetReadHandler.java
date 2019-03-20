package com.yanbenjun.file.parse.core.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.yanbenjun.file.config.element.ToParseFile;
import com.yanbenjun.file.model.parse.ColumnEntry;
import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.ParseStartHandler;
import com.yanbenjun.file.parse.message.ParseContext;

public class XlsxSheetReadHandler extends DefaultHandler {

    private ToParseFile toParseFile;

    /**
     * 解析完一行的后置处理链
     */
    private ParseStartHandler startHandler;

    private ParseContext parseContext;

    protected SharedStringsTable sst;

    private String lastContents;

    private boolean nextIsString;

    protected String r;

    protected boolean newLine; // 当前是否新的行

    protected int sheetIndex; // 当前页码

    protected int row; // 行指针

    protected int column; // 列指针

    private List<ColumnEntry> dataRow = new ArrayList<ColumnEntry>();

    XlsxSheetReadHandler(ToParseFile toParseFile, SharedStringsTable sst, ParseContext parseContext) {
        this.toParseFile = toParseFile;
        this.sst = sst;
        this.parseContext = parseContext;
    }

    XlsxSheetReadHandler(ToParseFile toParseFile, ParseStartHandler startHandler, SharedStringsTable sst,
            ParseContext parseContext) {
        this.toParseFile = toParseFile;
        this.startHandler = startHandler;
        this.sst = sst;
        this.parseContext = parseContext;
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        // c => cell
        if (name.equals("c")) {
            r = attributes.getValue("r");
            setPosition(r);
            // Figure out if the value is an index in the SST
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
        // Clear contents cache
        lastContents = "";
    }

    public void endElement(String uri, String localName, String name) throws SAXException {
        // Process the last contents as required.
        // Do now, as characters() may be called more than once
        if (nextIsString) {
            int idx = Integer.parseInt(lastContents);
            lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            nextIsString = false;
        }

        // v => contents of a cell
        // Output after we've seen the string contents
        if (name.equals("v")) {
            if (newLine) {
                try {
                    compute(dataRow, false);
                } finally {
                    dataRow.clear();// 抛出任何行操作异常，均情况行数据缓存，否则会有垃圾数据
                }
            }
            dataRow.add(new ColumnEntry(this.column, lastContents));
        }
    }

    public void endDocument() throws SAXException {
        try {
            compute(dataRow, true);
        } finally {
            dataRow.clear(); // 抛出任何行操作异常，均情况行数据缓存，否则会有垃圾数据
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        lastContents += new String(ch, start, length);
    }

    private void compute(List<ColumnEntry> dataRow, boolean lastRow) throws RowHandleException {
        int curRow = lastRow ? this.row : this.row - 1; // 最后一列时，当前行指针指向当前列，其余情况指针指向下一行
        ParsedRow prow = new ParsedRow(toParseFile.getTemplateWith(sheetIndex), curRow);
        prow.setRowIndex(curRow);
        prow.setCells(dataRow);
        prow.setLastRow(lastRow);

        parseContext.clearMessage();// 重新开始新的一行消息
        startHandler.processOne(prow, parseContext);
    }

    private void setPosition(String r) {
        int curRow = Integer.parseInt(r.replaceAll("[A-Z]+", "")) - 1;
        int curCol = convertChars2Num(r.replaceAll("\\d+", "")) - 1;
        if (curRow > this.row) {
            this.newLine = true;
        } else {
            this.newLine = false;
        }
        this.row = curRow;
        this.column = curCol;
    }

    private static int convertChars2Num(String characters) {
        int firstNum = 'A';
        int total = 0;
        char[] chars = characters.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length; i++) {
            char c = chars[length - i - 1];
            total += (c - firstNum + 1) * (int) Math.pow(26, i);
        }
        return total;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public ParseStartHandler getStartHandler() {
        return startHandler;
    }

    public void setStartHandler(ParseStartHandler startHandler) {
        this.startHandler = startHandler;
    }

    public ParseContext getParseContext() {
        return parseContext;
    }
}
