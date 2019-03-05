package com.yanbenjun.file.parse.message;

import java.util.ArrayList;
import java.util.List;

public class RowParseMessage extends ParseMessage
{
    private int rowIndex;
    
    private List<CellParseMessage> cellParseMsgs = new ArrayList<CellParseMessage>();
    
    public RowParseMessage(int rowIndex)
    {
        this.rowIndex = rowIndex;
    }
    
    public void add(CellParseMessage cellParseMessage)
    {
        cellParseMsgs.add(cellParseMessage);
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

    public void setCellParseMsgs(List<CellParseMessage> cellParseMsgs)
    {
        this.cellParseMsgs = cellParseMsgs;
    }

    @Override
    public boolean breakOut()
    {
        return false;
    }
}
