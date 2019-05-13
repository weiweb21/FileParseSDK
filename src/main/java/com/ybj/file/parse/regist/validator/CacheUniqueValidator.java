package com.ybj.file.parse.regist.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ybj.file.util.MapUtils;

public abstract class CacheUniqueValidator extends UniqueValidator
{
    /**
     * 该Field已经存在的值的集合,包括数据库中和已解析的
     */
    private List<Map<String, Object>> existValues = new ArrayList<>();
    
    public CacheUniqueValidator()
    {
        super();
        existValues.addAll(queryExistValues());
    }

    public abstract List<Map<String, Object>> queryExistValues();

    @Override
    public boolean validate(Map<String, Object> checkingColumns)
    {
        for (Map<String, Object> existValue : existValues)
        {
            if (MapUtils.equals(checkingColumns, existValue))
            {
                return true;
            }
        }
        existValues.add(checkingColumns);
        return false;
    }
}
