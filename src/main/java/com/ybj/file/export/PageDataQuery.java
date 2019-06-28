package com.ybj.file.export;

import java.util.List;
import java.util.Map;

public interface PageDataQuery {

    public List<Map<String, Object>> query(int pageNum);
}
