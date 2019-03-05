package com.yanbenjun.file.parse.message;

import java.util.ArrayList;
import java.util.List;

import com.yanbenjun.file.model.ErrorMessage;

public class HeadParseMessage extends ParseMessage
{
    private List<ErrorMessage> errorMsgs = new ArrayList<ErrorMessage>();
    
    public HeadParseMessage()
    {
        
    }

    @Override
    public boolean breakOut()
    {
        return false;
    }
    
    public void add(ErrorMessage em)
    {
        errorMsgs.add(em);
    }
    
    public void addAll(List<ErrorMessage> ems)
    {
        errorMsgs.addAll(ems);
    }

    public List<ErrorMessage> getErrorMsgs()
    {
        return errorMsgs;
    }
    
    public String toString() {
        return errorMsgs.toString();
    }
}
