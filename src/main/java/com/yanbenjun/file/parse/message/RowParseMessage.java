package com.yanbenjun.file.parse.message;

import java.util.ArrayList;
import java.util.List;

public class RowParseMessage extends ParseMessage
{
    private int rowIndex;
    
    private String rowMsg;

    private List<CellParseMessage> cellParseMsgs = new ArrayList<CellParseMessage>();
    
    public RowParseMessage() {
    }

    public RowParseMessage(int rowIndex)
    {
        this.rowIndex = rowIndex;
    }
    
    @Override
    public boolean isHasError()
    {
        return !cellParseMsgs.isEmpty();
    }

    public int getRowIndex()
    {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex)
    {
        this.rowIndex = rowIndex;
    }
    
    public List<CellParseMessage> getCellParseMsgs()
    {
        return cellParseMsgs;
    }

    public void add(CellParseMessage em) {
        cellParseMsgs.add(em);
    }

    public void addAll(List<CellParseMessage> ems) {
        cellParseMsgs.addAll(ems);
    }

    @Override
    public boolean breakOut()
    {
        return false;
    }

    public String getRowMsg() {
        return rowMsg;
    }

    public void setRowMsg(String rowMsg) {
        this.rowMsg = rowMsg;
    }
}
