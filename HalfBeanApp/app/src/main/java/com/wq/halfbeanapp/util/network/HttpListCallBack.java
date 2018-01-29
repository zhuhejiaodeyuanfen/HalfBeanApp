package com.wq.halfbeanapp.util.network;

import java.util.List;

/**
 * Created by vivianWQ on 2018/1/29
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public abstract class HttpListCallBack<T> {

    public abstract void onSuccess(List<T> data);

    public abstract void onFail(String msg);

}