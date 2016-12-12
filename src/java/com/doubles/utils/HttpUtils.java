package com.doubles.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HttpUtils {
    public static int doGet(String url, ReqApi params, StringBuffer out) {
        int statusCode = 0;
        HttpClient client = null;
        HttpMethod method = null;
        try {
            client = new HttpClient();
            if (params != null && params.isTimeOut()) {
                client.getHttpConnectionManager().getParams().setConnectionTimeout(params.getCon_timeOut());
            }
            method = new GetMethod(url);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, params.getReq_charset());
            if (params != null && params.isTimeOut()) {
                method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, params.getSo_timeOut());
            }
            if (params != null && !params.isEmpty()) {
                method.setQueryString(params.paramsToString());
            }
            client.executeMethod(method);
            statusCode = method.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {

                InputStream inputStream = method.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream, params.getRes_charset()));
                String str = "";
                while ((str = br.readLine()) != null) {
                    out.append(str);
                }
                inputStream.close();

            }
        } catch (URIException e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } catch (IOException e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } catch (Exception e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return statusCode;
    }

    public static int doGet(String url, String params, StringBuffer out) {
        int statusCode = 0;
        HttpClient client = null;
        HttpMethod method = null;
        try {
            client = new HttpClient();

            method = new GetMethod(url);
            if (StringUtils.isNotBlank(params)) {
                method.setQueryString(params);
            }
            client.executeMethod(method);
            statusCode = method.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {

                InputStream inputStream = method.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream, "UTF-8"));
                String str = "";
                while ((str = br.readLine()) != null) {
                    out.append(str);
                }
                inputStream.close();

            }
        } catch (URIException e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } catch (IOException e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } catch (Exception e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } finally {
            if (method != null)
                method.releaseConnection();
        }
        return statusCode;
    }

    public static int doPost(String url, ReqApi params, StringBuffer out) {
        int statusCode = 0;
        HttpClient client = null;
        PostMethod method = null;
        try {
            client = new HttpClient();

            if (params.isTimeOut()) {
                client.getHttpConnectionManager().getParams().setConnectionTimeout(params.getCon_timeOut());
            }
            method = new PostMethod(url);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, params.getReq_charset());
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + params.getReq_charset());
            if (params.isTimeOut()) {
                method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, params.getSo_timeOut());
            }
            if (!params.isEmpty()) {
                method.setRequestBody(params.paramsToNameValuePair());
            }

            client.executeMethod(method);
            statusCode = method.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {

                InputStream inputStream = method.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream, params.getRes_charset()));
                String str = "";
                while ((str = br.readLine()) != null) {
                    out.append(str);
                }
                inputStream.close();

            }
        } catch (URIException e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } catch (IOException e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } catch (Exception e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } finally {
            if (method != null)
                method.releaseConnection();
        }
        return statusCode;
    }

    public static int doPostRest(String url, ReqApi params, String bodyContent, StringBuffer out) {
        int statusCode = 0;
        DefaultHttpClient client = null;

        try {
            client = new DefaultHttpClient();

            if (params != null && !params.isEmpty()) {
                url += "?" + params.paramsToString();
            }
            HttpPost httpost = new HttpPost(url);

            //httpost.
            httpost.setHeader(HttpMethodParams.HTTP_CONTENT_CHARSET, params.getReq_charset());
            httpost.setHeader("Content-Type", "application/json;charset=" + params.getReq_charset());

            httpost.setEntity(new StringEntity(bodyContent, params.getReq_charset()));
            HttpResponse response = client.execute(httpost);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        httpEntity.getContent(), params.getRes_charset()));
                String str = "";
                while ((str = br.readLine()) != null) {
                    out.append(str);
                }


            }
        } catch (URIException e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } catch (IOException e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } catch (Exception e) {
            Utils.logExceptionStack(e);
            return statusCode;
        } finally {

        }
        return statusCode;
    }


}
