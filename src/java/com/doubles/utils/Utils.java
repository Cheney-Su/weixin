package com.doubles.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils工具类
 * Created by Administrator on 2016/7/12.
 * USER: Suhuaqiang
 */
public class Utils {

    public static String md5(String v) {
        try {
            v = new String(DigestUtils.md5Hex(v.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return v;
    }

    public static String encodeBase64(String v) {
        return new String(Base64.encodeBase64(v.getBytes(), true));
    }

    public static String decodeBase64(String v) {
        return new String(Base64.decodeBase64(v.getBytes()));
    }

    public static String createOrderId() {
        String randomStr = "";
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(new Date());
        randomStr = time + genRandomNum(10);
        return randomStr;
    }

    /**
     * 验证手机号是否输入正确
     *
     * @param mobile
     * @return true正确，false错误
     */
    public static boolean judgeMobile(String mobile) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 获取Map的字典排序结果字符串
     *
     * @param map
     * @return
     */
    public static String getDictSortStrFromMap(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.putAll(map);

        StringBuilder sb = new StringBuilder("");
        for (String key : treeMap.keySet()) {
            sb.append("&").append(key).append("=").append(treeMap.get(key));
        }
        return sb.substring(1);
    }

    public static String getUrlParamsFromMap(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder("");
        for (Object keyObj : map.keySet()) {
            String keyStr = String.valueOf(keyObj);
            String value = map.get(keyStr);
            sb.append("&").append(keyStr).append("=").append(value);
        }
        return sb.substring(1);
    }

//    /**
//     * 请求唯一标识
//     */
//    public static String genRequestId() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("(").append(Constants.SDF_YYYY_MM_DD_HH_MM_SS.format(new Date())).append(":")
//                .append(genRandomNum(6)).append(")");
//        return sb.toString();
//    }

    /**
     * 随机数字串
     *
     * @param length
     * @return
     */
    public static String genRandomNum(int length) {
        String random = RandomStringUtils.randomNumeric(length);
        return random;
    }

    /**
     * 随机大小字母+数字串
     *
     * @param length
     * @return
     */
    public static String genCharNumRandom(int length) {
        String random = RandomStringUtils.randomAlphanumeric(length);
        return random;
    }

    /**
     * 随机小写字母+数字串
     *
     * @param length
     * @return
     */
    public static String genLowerCharNumRandom(int length) {
        String random = RandomStringUtils.randomAlphanumeric(length).toLowerCase();
        return random;
    }

    /**
     * 随机小写字母串
     *
     * @param length
     * @return
     */
    public static String genLowerCharRandom(int length) {
        String random = RandomStringUtils.randomAlphabetic(length).toLowerCase();
        return random;
    }

    /**
     * 随机大写字母串
     *
     * @param length
     * @return
     */
    public static String genUpperCharRandom(int length) {
        String random = RandomStringUtils.randomAlphabetic(length).toUpperCase();
        return random;
    }

    public static String getMockString(String fileName) {
        Utils util = new Utils();
        String rootPath = util.getClass().getResource("/").getPath() + "mock" + File.separator;
        File file = new File(rootPath + fileName);
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp = null;
            StringBuffer sb = new StringBuffer();
            temp = br.readLine();
            while (temp != null) {
                sb.append(temp + " ");
                temp = br.readLine();
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getMockJson(String fileName) {
        try {
            String str = getMockString(fileName);
            return JSONObject.fromObject(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONArray getMockJsonArray(String fileName) {
        try {
            String str = getMockString(fileName);
            return JSONArray.fromObject(str);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 判断是否为空(String/Collection/Map)
     *
     * @param obj
     * @return
     */
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

    /**
     * 判断是否为数字
     *
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {
        try {
            Integer.valueOf(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为JSONObject格式
     *
     * @param s
     * @return
     */
    public static boolean isJSONObject(String s) {
        try {
            JSONObject.fromObject(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 检测混合字符串长度是否在指定范围之内
     *
     * @param s
     * @param limitMinLen 汉字长度限制
     * @param limitMaxLen 汉字长度限制
     * @param charRate    单个汉字最多可换charRate个英文字符
     * @return
     */
    public static boolean checkStrInLimitLength(String s, int limitMinLen, int limitMaxLen, int charRate) {
        limitMaxLen = limitMaxLen * charRate;
        StringBuilder r_s = new StringBuilder("");
        for (int i = 0; i < charRate; i++) {
            r_s.append("*");
        }
        String tmpStr = s.replaceAll("[^\\x00-\\xff]", r_s.toString());
        if (tmpStr.length() >= limitMinLen && tmpStr.length() <= limitMaxLen) {
            return true;
        }
        return false;
    }

    /**
     * 返回32位UUID随机字符串
     *
     * @return
     */
    public static String gen32UUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    /**
     * 返回16位UUID随机字符串
     *
     * @return
     */
    public static String gen16UUID() {
        String uuid = UUID.randomUUID().toString();
        String s = null;
        try {
            s = new String(DigestUtils.md5Hex(uuid.replaceAll("-", "").getBytes("UTF-8"))).substring(8, 24);
        } catch (Exception e) {
            return null;
        }
        return s;
    }

    public static List<JSONObject> getPageList(List<JSONObject> list, String lastSID, int pageSize) {
        if (Utils.isEmpty(list) || pageSize == 0) {
            return null;
        } else if (pageSize < 0) {
            //返回全部
            return list;
        }
        int start = -1;
        int end = 0;
        if (!Utils.isEmpty(lastSID)) {
            for (int i = 0; i < list.size(); i++) {
                if (lastSID.equals(list.get(i).getString("SID"))) {
                    start = i + 1;
                    break;
                }
            }
        }
        if (Utils.isEmpty(lastSID)) {
            start = 0;
        } else if (start == -1) {
            return null;
        }
        if (list.size() <= start) {
            return null;
        }
        end = start + pageSize;
        if (end > list.size()) {
            end = list.size();
        }
        List<JSONObject> newList = new ArrayList<JSONObject>();
        for (int i = start; i < end; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    public static String seatIds2SeatNames(String seatIds) {
        if (Utils.isEmpty(seatIds)) {
            return "";
        }
        String seatNames = "";
        String[] seatIdArr = seatIds.split("\\|");
        for (String seatId : seatIdArr) {
            String row = seatId.split(":")[0];
            String column = seatId.split(":")[1];
            seatNames += "|" + row + "排" + column + "座";
        }
        return seatNames.substring(1);
    }

    public static String sortSeat(String seats) {
        if (Utils.isEmpty(seats)) {
            return null;
        }
        String[] seatArr = seats.split("\\|");
        List<String> list = new ArrayList<String>();
        for (String seat : seatArr) {
            list.add(seat);
        }
        //顺序排
        Collections.sort(list, new Comparator<String>() {
            public int compare(String o1, String o2) {
                if (o1.length() != o2.length()) {
                    int diff = o1.length() - o2.length();
                    if (diff == 0) {
                        return 0;
                    } else if (diff > 0) {
                        return 1;
                    }
                    return -1;
                }
                int diff = o1.compareTo(o2);
                if (diff == 0) {
                    return 0;
                } else if (diff > 0) {
                    return 1;
                }
                return -1;
            }
        });
        seats = "";
        for (String seat : list) {
            seats += "|" + seat;
        }
        return seats.substring(1);
    }

    /**
     * 删除Html标签
     *
     * @param inputString
     * @return
     */
    public static String htmlRemoveTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            htmlStr = htmlStr.replaceAll("\n", "");
            htmlStr = htmlStr.replaceAll("[\t\r]", " ");
            textStr = htmlStr.trim();
        } catch (Exception e) {
            Utils.logExceptionStack(e);
        }
        return textStr;// 返回文本字符串
    }

    /**
     * 获取客户端IP(直连情况，中间没有类似nginx负载均衡代理)
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.trim().length() > 15) {
            ip = ip.trim().split(",")[0];
        }
        return ip;
    }

    /**
     * 获取客户端登录IP(有类似nginx负载均衡代理的情况)
     *
     * @param request
     * @return
     */
    public static String getNewIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            String[] ips = ip.trim().split(",");
            //获取倒数第二个ip
            if (ips.length >= 2) {
                ip = ips[ips.length - 2];
            } else {
                ip = ips[0];
            }
        }
        return ip.trim();
    }

    /**
     * 根据出生时间毫秒获取年龄
     *
     * @param birthday
     * @return
     */
    public static int getAge(long birthday) {
        if (birthday == 0) {
            return 0;
        }
        Calendar c_now = Calendar.getInstance();
        Calendar c_birth = Calendar.getInstance();
        c_birth.setTimeInMillis(birthday);
        int age = c_now.get(Calendar.YEAR) - c_birth.get(Calendar.YEAR);
        if (age < 0) {
            age = 0;
        }
        return age;
    }

    public static Map<String, String> getChannelMap(int channel) {
        Map<String, String> map = new HashMap<String, String>();
        switch (channel) {
            case 29:
                // 安卓APP客户端
                map.put("bid", "1");
                map.put("key", "mzKHClient518");
                break;
            case 31:
                // IOSAPP客户端
                map.put("bid", "1");
                map.put("key", "mzKHClient518");
                break;
            case 33:
                // H5
                map.put("bid", "3");
                map.put("key", "mzKHClient518");
                break;
            case 55:
                // 安卓鱼丸APP
                map.put("bid", "514");
                map.put("key", "mzKHClient518");
                break;
            case 56:
                // IOS鱼丸APP
                map.put("bid", "515");
                map.put("key", "mzKHClient518");
                break;
            case 62:
                // H5鱼丸
                map.put("bid", "522");
                map.put("key", "mzKHClient518");
                break;
            default:
                map = null;
                break;
        }
        return map;
    }

    /**
     * xml自动转换的JSON的属性去除@符
     *
     * @param json
     * @return
     */
    public static JSONObject xmlJson2Json(JSONObject json) {
        JSONObject resJson = new JSONObject();
        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = json.get(key);
            if (key.startsWith("@")) {
                //去掉内部属性@符号
                key = key.substring(1);
            }
            resJson.put(key, value);
        }
        return resJson;
    }

    private static int VIRTUALFORETELLID_MIN = 30000000;
    private static int VIRTUALFORETELLID_MAX = 60000000;

    public static boolean isRealForetell(String foretellId) {
        if (isEmpty(foretellId)) {
            return false;
        }
        int foretellId_i = Integer.valueOf(foretellId);
        boolean isRealForetell = foretellId_i < VIRTUALFORETELLID_MIN || foretellId_i > VIRTUALFORETELLID_MAX;
        return isRealForetell;
    }

    public static boolean checkIp(String ip) {
        if (ip.length() < 7 || ip.length() > 15 || Utils.isEmpty(ip)) {
            return false;
        }
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern p = Pattern.compile(rexp);
        Matcher m = p.matcher(ip);
        boolean isValid = m.matches();
        return isValid;
    }

    /**
     * 获取指定区间不重复的n个数字
     *
     * @param min
     * @param max
     * @param n
     * @return
     */
    public static int[] getRandomNoRepeat(int min, int max, int n) {
        if (n <= 0) {
            return new int[0];
        }
        if (n > max - min + 1) {
            n = max - min + 1;
        }
        int[] arr = new int[max - min + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < n; i++) {
            int index = (int) (Math.random() * arr.length);
            int tmp = arr[i];
            arr[i] = arr[index];
            arr[index] = tmp;
        }
        int[] result = new int[n];
        for (int i = 0; i < result.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    private static Logger logger = Logger.getLogger(Utils.class);

    public static void logExceptionStack(Exception e) {
        logger.error("exception==stack==", e);
    }
}
