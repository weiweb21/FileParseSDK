package com.ybj.file.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ybj.file.parse.regist.validator.datasource.DbEnumDataSource;

public class RoomNameEnumDataSource extends DbEnumDataSource {

    @Override
    public List<Map<String, Object>> queryDataList(String... args) {
        return new ArrayList<>();
    }

}
