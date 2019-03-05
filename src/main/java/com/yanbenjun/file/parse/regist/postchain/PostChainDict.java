package com.yanbenjun.file.parse.regist.postchain;

import java.util.HashMap;
import java.util.Map;

import com.yanbenjun.file.parse.core.PostHandleChain;
import com.yanbenjun.file.parse.regist.ICanRegist;

/**
 * PostHandleChain缓存
 * 
 * @author yanbenjun
 *
 */
public class PostChainDict implements ICanRegist {

    public static final String REGIST_KEY = "POST_HANDLE_CHAIN";

    private Map<String, PostHandleChain> cache = new HashMap<>();
    @Override
    public String getRegistKey() {
        return REGIST_KEY;
    }

}
