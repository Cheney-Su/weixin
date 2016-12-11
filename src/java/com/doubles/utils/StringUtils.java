package com.doubles.utils;

/**
 * StringUtils工具类
 * Created by Administrator on 2016/7/12.
 * USER: Suhuaqiang
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str))
            return true;
        return false;
    }
}
