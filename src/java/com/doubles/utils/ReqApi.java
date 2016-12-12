package com.doubles.utils;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

public class ReqApi extends TreeMap<String, String> {
    public final static String HTTP_METHOD_POST = "POST";
    public final static String HTTP_METHOD_GET = "GET";

    public final static String HTTP_DEFAULT_CHARSET = "utf-8";

    public static final int HTTP_CONNECTION_TIMEOUT = 60000;
    public static final int HTTP_SO_TIMEOUT = 300000;

    private String httpMethod = HTTP_METHOD_GET;
    private String req_charset = HTTP_DEFAULT_CHARSET;
    private String res_charset = HTTP_DEFAULT_CHARSET;
    private boolean timeOut = false;
    private int so_timeOut = HTTP_SO_TIMEOUT;
    private int con_timeOut = HTTP_CONNECTION_TIMEOUT;

    public String addParam(String key, String value) {
        if ((value == null))
            return null;
        return super.put(key, value);
    }

    public String addParam(String key, int value) {
        return super.put(key, Integer.toString(value));
    }

    public String addParam(String key, long value) {
        return super.put(key, Long.toString(value));
    }

    public String addParam(String key, short value) {
        return super.put(key, Short.toString(value));
    }

    public String addParam(String key, byte value) {
        return super.put(key, Byte.toString(value));
    }

    public String removeParam(String key) {
        if (!super.containsKey(key))
            return null;
        return super.remove(key);
    }

    public String paramsToString() {
        if (super.isEmpty())
            return null;
        StringBuffer buffer = new StringBuffer();

        for (Map.Entry<String, String> i : super.entrySet()) {

            if (StringUtils.isNotBlank(buffer.toString()))
                buffer.append('&');

            buffer.append(i.getKey());
            buffer.append('=');
            try {
                buffer.append(urlencode(i.getValue()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }

        return buffer.toString();
    }


    private String urlencode(String str) throws UnsupportedEncodingException {
        String rc = URLEncoder.encode(str, req_charset);
        return rc.replace("*", "%2A");
    }

    public NameValuePair[] paramsToNameValuePair() {
        if (super.isEmpty())
            return null;

        NameValuePair[] pairs = new NameValuePair[super.size()];
        int index = 0;
        for (Map.Entry<String, String> i : super.entrySet()) {

            pairs[index] = new NameValuePair(i.getKey(), i.getValue());
            index++;
        }
        return pairs;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getReq_charset() {
        return req_charset;
    }

    public void setReq_charset(String req_charset) {
        this.req_charset = req_charset;
    }

    public String getRes_charset() {
        return res_charset;
    }

    public void setRes_charset(String res_charset) {
        this.res_charset = res_charset;
    }

    public boolean isTimeOut() {
        return timeOut;
    }

    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }

    public int getSo_timeOut() {
        return so_timeOut;
    }

    public void setSo_timeOut(int so_timeOut) {
        this.so_timeOut = so_timeOut;
    }

    public int getCon_timeOut() {
        return con_timeOut;
    }

    public void setCon_timeOut(int con_timeOut) {
        this.con_timeOut = con_timeOut;
    }


}
