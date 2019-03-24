package com.yanbenjun.file.util;

import java.util.Map;
import java.util.Map.Entry;

public class MapUtils
{
    public static boolean equals(Map<String, Object> map1, Map<String, Object> map2) {
        if(map1.size() == map2.size()) {
            for (Entry<String, Object> entry : map1.entrySet()) {
                if(entry.getValue() == null && map2.get(entry.getKey()) != null) {
                    return false;
                }
                if(entry.getValue() != null && !entry.getValue().equals(map2.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
}
