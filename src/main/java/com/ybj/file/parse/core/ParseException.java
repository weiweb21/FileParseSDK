package com.ybj.file.parse.core;

public class ParseException extends Exception
{

    private String error;
    /**
     * 
     */
    private static final long serialVersionUID = 6801037286673562840L;
    
    public ParseException() {
        
    }
    
    public ParseException(String error) {
        super(error);
        this.error = error;
    }

    public ParseException(Exception e) {
        super(e);
    }

    public String getError() {
        return this.error;
    }
}
