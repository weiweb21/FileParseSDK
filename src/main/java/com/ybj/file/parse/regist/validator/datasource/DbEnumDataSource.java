package com.ybj.file.parse.regist.validator.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public abstract class DbEnumDataSource implements EnumDataSource {

    //@Autowired
    private DataSource dataSource;

    @Override
    public Map<String, Object> getKeyvalueMap(String... args) {
        Map<String, Object> cache = new HashMap<>();
        queryDataList().forEach(item -> {
            cache.put((String) item.get("fdKey"), item.get("fdValue"));
        });
        return cache;
    }

    /**
     * 获取形如[{"fdKey":enumKey,"fdValue":enumValue},{...}]的list
     * @param args
     * @return
     */
    public abstract List<Map<String, Object>> queryDataList(String... args);

    public DataSource getDataSource() {
        return this.dataSource;
    }

}
