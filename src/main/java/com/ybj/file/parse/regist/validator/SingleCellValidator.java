package com.ybj.file.parse.regist.validator;

public interface SingleCellValidator extends Validator {

    /**
     * 校验Field的值是否符合条件
     * @param value 需要校验的值
     * @return true=不通过，=通过
     */
    public boolean validate(String value);

    /**
     * 校验完毕获取错误信息
     * @return
     */
    public String getErrorMsg();

    /**
     * 如果校验正确，则获取转换后的值，校验错误，则返回空
     * 
     * @param 原始字符串
     * @return 转换后的值
     */
    public Object getTypeValue(String originalValue);
}
