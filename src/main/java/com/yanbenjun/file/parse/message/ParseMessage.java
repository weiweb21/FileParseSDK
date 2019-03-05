package com.yanbenjun.file.parse.message;

public abstract class ParseMessage implements IParseMessage
{
    protected boolean hasError;

    public boolean isHasError()
    {
        return hasError;
    }

    public void setHasError(boolean hasError)
    {
        this.hasError = hasError;
    }
}
