package com.yanbenjun.file.parse.regist.validator;

import com.yanbenjun.file.parse.regist.convertor.TypeConvertor;

public interface SingleCellValidator extends Validator, TypeConvertor<Object> {

    /**
     * 校验Field的值是否符合条件
     * @param value 需要校验的值
     * @return true=不通过，=通过
     */
    public boolean validate(String originalValue);
    
    /**
     * 校验正确后，将原始值转换为需要的类型值。
     */
    public Object convert(String originalValue);

    /**
     * 校验完毕获取错误信息
     * @return
     */
    public String getErrorMsg();
}
