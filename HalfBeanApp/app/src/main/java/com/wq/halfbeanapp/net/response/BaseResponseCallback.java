package com.wq.halfbeanapp.net.response;

/**
 * Created by vivianWQ on 2017/8/2
 * Mail: wangqi_vivian@sina.com
 * desc: responseCallBack的基类,所有记录日志的接口都通过调用saveNetLog调用
 * Version: 1.0
 */
public abstract class BaseResponseCallback {


    public abstract void onResponseFail(String errorString);

    public abstract void onSuccess(String response);
}
