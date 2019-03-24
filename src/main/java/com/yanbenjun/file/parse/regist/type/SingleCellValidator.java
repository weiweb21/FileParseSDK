package com.yanbenjun.file.parse.regist.type;

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
}
