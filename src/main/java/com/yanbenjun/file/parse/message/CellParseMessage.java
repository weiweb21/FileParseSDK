package com.yanbenjun.file.parse.message;

public class CellParseMessage extends ParseMessage
{
    private int columnIndex;
    
    private int rowIndex;
    
    private int sheetIndex;
    
    private int fileId;
    
    private String msg;
    
    public CellParseMessage(String msg, int columnIndex, int rowIndex, int sheetIndex)
    {
        this.msg = msg;
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        this.sheetIndex = sheetIndex;
    }

    public int getColumnIndex()
    {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex)
    {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex()
    {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex)
    {
        this.rowIndex = rowIndex;
    }

    public int getSheetIndex()
    {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex)
    {
        this.sheetIndex = sheetIndex;
    }

    public int getFileId()
    {
        return fileId;
    }

    public void setFileId(int fileId)
    {
        this.fileId = fileId;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public boolean breakOut()
    {
        return false;
    }
}
