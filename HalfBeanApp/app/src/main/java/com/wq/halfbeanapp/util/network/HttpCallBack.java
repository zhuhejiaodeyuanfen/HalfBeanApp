package com.wq.halfbeanapp.util.network;

/**
 * Created by vivianWQ on 2018/1/29
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public abstract class HttpCallBack<T> {

    public abstract void onSuccess(T data);

    public abstract void onFail(String msg);
}