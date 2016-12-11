package com.doubles.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Utils工具类
 * Created by Administrator on 2016/7/12.
 * USER: Suhuaqiang
 */
public class Utils {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return "".equals(String.valueOf(obj).trim());
        } else if (obj instanceof Collection) {
            return ((Collection) obj).size() == 0;
        } else if (obj instanceof Map) {
            return ((Map) obj).size() == 0;
        }
        return false;
    }
}
