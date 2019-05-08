package com.ybj.file.parse.regist;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRegister implements Register
{

    protected static final Map<String, ICanRegist> CONTAINER = new ConcurrentHashMap<>();

    @Override
    public final boolean regist(ICanRegist t)
    {
        return CONTAINER.putIfAbsent(t.getRegistKey() + "_" + this.getClass().getName(), t) == null ? true : false;
    }

    public ICanRegist getObject(String registKey)
    {
        return CONTAINER.get(registKey + "_" + this.getClass().getName());
    }

    @Override
    public boolean regist(String key, ICanRegist t) {
        return CONTAINER.putIfAbsent(key + "_" + this.getClass().getName(), t) == null ? true : false;
    }

}
