package com.doubles.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Administrator on 2016/7/12.
 * USER: Suhuaqiang
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Result {

    private int status;   //数据请求状态
    private Object data;  //业务数据
    private String msg;   //业务描述

    public Result() {
    }

    public Result(int status, Object data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}