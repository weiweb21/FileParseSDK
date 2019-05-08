package com.ybj.file.parse.message;

import java.util.ArrayList;
import java.util.List;

public class RowParseMessage extends ParseMessage
{
    private int rowIndex;
    
    private String rowMsg;

    private List<CellParseMessage> cellParseMsgs = new ArrayList<>();

    private List<MultiCellsParseMessage> multiCellsParseMsgs = new ArrayList<>();

    public RowParseMessage() {
    }

    public RowParseMessage(int rowIndex)
    {
        this.rowIndex = rowIndex;
    }
    
    @Override
    public boolean isHasError()
    {
        return !cellParseMsgs.isEmpty() || super.isHasError();
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

    public void addMulti(MultiCellsParseMessage em) {
        multiCellsParseMsgs.add(em);
    }

    public void addMultiAll(List<MultiCellsParseMessage> ems) {
        multiCellsParseMsgs.addAll(ems);
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
