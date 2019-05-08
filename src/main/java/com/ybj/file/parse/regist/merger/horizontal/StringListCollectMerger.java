package com.ybj.file.parse.regist.merger.horizontal;

import java.util.ArrayList;
import java.util.List;

import com.ybj.file.parse.regist.merger.vertical.TypeVerticalMerger;

public class StringListCollectMerger implements TypeVerticalMerger<List<String>>, TypeHorizontalMerger<List<String>> {

    public static final String REGIST_KEY = "stringlist";
    @Override
    public String getRegistKey()
    {
        return REGIST_KEY;
    }

    @Override
    public List<String> merge(Object... objects)
    {
        List<String> result = new ArrayList<>();
        for (Object obj : objects) {
            if (obj == null) {
                result.add(null);
            } else if (obj instanceof String) {
                result.add((String) obj);
            } else {
                throw new RuntimeException("水平合并时数据类型错误");
            }
        }
        return result;
    }
}
