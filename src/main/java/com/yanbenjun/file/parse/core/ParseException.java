package com.yanbenjun.file.parse.core;

public class ParseException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 6801037286673562840L;
    
    public ParseException() {
        
    }
    
    public ParseException(Exception e) {
        super(e);
    }
}
