package com.yanbenjun.file.parse.regist.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yanbenjun.file.util.MapUtils;

/**
 * TODO
 * @author Administrator
 *
 */
public class UniqueValidator implements MultiCellValidator
{
    public static final String REGIST_KEY = "unique";

    private String uniqueValidatorExpression;

    private List<String> unionKeys = new ArrayList<>();
    /**
     * 该Field已经存在的值的集合,包括数据库中和已解析的
     */
    private List<Map<String, Object>> existValues = new ArrayList<>();

    public UniqueValidator(String uniqueValidatorExpression)
    {
        this.uniqueValidatorExpression = uniqueValidatorExpression;
    }

    public void init()
    {

    }

    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

    @Override
    public List<String> getUnionKeys()
    {
        return this.unionKeys;
    }

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

    @Override
    public String getErrorMsg()
    {
        // TODO Auto-generated method stub
        return "重复的数据";
    }

    public String getUniqueValidatorExpression()
    {
        return uniqueValidatorExpression;
    }

    public void setUniqueValidatorExpression(String uniqueValidatorExpression)
    {
        this.uniqueValidatorExpression = uniqueValidatorExpression;
    }
}
