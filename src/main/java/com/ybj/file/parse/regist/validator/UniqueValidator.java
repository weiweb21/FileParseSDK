package com.ybj.file.parse.regist.validator;

import java.util.ArrayList;
import java.util.List;

public abstract class UniqueValidator implements MultiCellValidator {

    public static final String REGIST_KEY = "unique";

    private List<String> unionKeys = new ArrayList<>();

    public UniqueValidator(List<String> unionKeys) {
        this.unionKeys.addAll(unionKeys);
    }

    @Override
    public String getSimpleName() {
        return REGIST_KEY;
    }

    @Override
    public List<String> getUnionKeys() {
        return this.unionKeys;
    }

    @Override
    public String getErrorMsg() {
        // TODO Auto-generated method stub
        return "重复的数据";
    }
}
