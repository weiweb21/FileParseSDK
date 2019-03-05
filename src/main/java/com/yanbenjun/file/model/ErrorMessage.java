package com.yanbenjun.file.model;

public class ErrorMessage extends Location
{
    private String error;
    
    public ErrorMessage() 
    {
        
    }
    
    public ErrorMessage(String error)
    {
        this.error = error;
    }

    public ErrorMessage(String error, int sheetId)
    {
        this(error, sheetId, -1);
    }

    public ErrorMessage(String error, int sheetId, int rowId)
    {
        this(error, sheetId, rowId, -1);
    }

    public ErrorMessage(String error, int sheetId, int rowId, int columnId)
    {
        super(sheetId, rowId, columnId);
        this.error = error;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String toString()
    {
        return "{"+super.toString() + ", error: " + this.error + "}";
    }
}
