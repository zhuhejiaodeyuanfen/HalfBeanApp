package com.wq.halfbeanapp.net.response;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 网络请求实体类,isSuccess判断是否成功
 */
public class ResponseBean implements Serializable {


    private int code;
    private String message;
    private Object data;

    public String getMessage() {
        return message;

    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isSuccess() {
        return getCode() == 200 ? true : false;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public <T> T getData(Class<T> cla) {
        if (isSuccess() && getData() != null && !TextUtils.isEmpty(getData().toString())) {
            return JsonTools.getBean(getData().toString(), cla);
        }
        return null;
    }

    public <T> List<T> getArrayData(Class<T> cla) {
        if (isSuccess() && getData() != null && !TextUtils.isEmpty(getData().toString())) {
            return JsonTools.getBeanList(getData().toString(), cla);
        }
        return null;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



}
