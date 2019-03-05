package com.yanbenjun.file.parse.regist.type;

import org.apache.commons.lang.StringUtils;

public class IntegerConverter implements TypeConvertor<Integer>
{
    public static final String REGIST_KEY = "integer";
    @Override
    public Integer convert(String value) throws TypeHandleException
    {
        if(StringUtils.isEmpty(value))
        {
            return null;
        }
        try
        {
            Integer result = Integer.parseInt(trimZeroAndDot(value.trim()));
            return result;
        }
        catch (NumberFormatException e)
        {
            TypeHandleException tce = new TypeHandleException("非整数类型");
            throw tce;
        }
    }
    
    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    private static String trimZeroAndDot(String s){
      if(s.indexOf(".") > 0){
        s = s.replaceAll("0+?$", "");//去掉多余的0
        s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
      }
      return s;
    }
    
    public static IntegerConverter singleton()
    {
        return IntegerConverterBuilder.singleton;
    }
    
    private static class IntegerConverterBuilder
    {
        private static final IntegerConverter singleton = new IntegerConverter();
    }

    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }


}
