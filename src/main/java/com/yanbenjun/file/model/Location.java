package com.yanbenjun.file.model;

public class Location
{
    private int fileId = -1;
    
    private int sheetId = -1;
    
    private int rowId = -1;
    
    private int columnId = -1;
    
    public Location()
    {
    }
    
    public Location(int sheetId)
    {
        this.sheetId = sheetId;
    }
    
    public Location(int sheetId,int rowId)
    {
        this.sheetId = sheetId;
        this.rowId = rowId;
    }
    
    public Location(int sheetId,int rowId, int columnId)
    {
        this.sheetId = sheetId;
        this.rowId = rowId;
        this.columnId = columnId;
    }
    
    public int getFileId()
    {
        return fileId;
    }

    public void setFileId(int fileId)
    {
        this.fileId = fileId;
    }

    public int getSheetId()
    {
        return sheetId;
    }

    public void setSheetId(int sheetId)
    {
        this.sheetId = sheetId;
    }

    public int getRowId()
    {
        return rowId;
    }

    public void setRowId(int rowId)
    {
        this.rowId = rowId;
    }

    public int getColumnId()
    {
        return columnId;
    }

    public void setColumnId(int columnId)
    {
        this.columnId = columnId;
    }
    
    public String toString() {
        return "Location: ["+fileId+","+sheetId+","+rowId+", "+columnId+"]";
    }
}
