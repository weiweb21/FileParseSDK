package com.yanbenjun.file.parse.regist.type;

import java.util.List;
import java.util.Map;

public interface MultiCellValidator extends Validator
{
    public List<String> getUnionKeys();
    
    public boolean validate(Map<String, Object> checkingColumns);

    /**
     * 校验完毕获取错误信息
     * @return
     */
    public String getErrorMsg();
}
