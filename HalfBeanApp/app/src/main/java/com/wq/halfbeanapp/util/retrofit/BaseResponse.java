package com.wq.halfbeanapp.util.retrofit;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class BaseResponse<T> implements Serializable {

    @JSONField(name = "code")
    public int code;
    @JSONField(name = "msg")
    public String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @JSONField(name = "data")

    public T data;
}