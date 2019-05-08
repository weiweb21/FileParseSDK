package com.ybj.file.util;


/**
 * 
 * @author yanbenjun
 *
 */
public class NumberUtils {

    /**
     * 使用java正则表达式去掉多余的.与0
     * 
     * @param s
     * @return
     */
    public static String trimZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }
}
