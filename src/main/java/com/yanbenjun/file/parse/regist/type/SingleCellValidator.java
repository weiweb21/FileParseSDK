package com.yanbenjun.file.parse.regist.type;

import com.yanbenjun.file.parse.regist.ICanRegist;

public interface SingleCellValidator extends ICanRegist {

    public boolean validate(String value);

    public String getErrorMsg();
}
