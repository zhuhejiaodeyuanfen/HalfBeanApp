package com.wq.halfbeanapp.net.response;


import com.wq.halfbeanapp.util.AppLogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vivianWQ on 2017/11/22
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class RoNetWorkUtil {

    private static RoNetWorkUtil roNetWorkUtil;

    public RoNetWorkUtil() {
    }

    public static RoNetWorkUtil getInstance() {
        if (roNetWorkUtil == null)
            roNetWorkUtil = new RoNetWorkUtil();
        return roNetWorkUtil;
    }

    public RoNetWorkUtil get(String url) {
        this.url = url;
        return this;
    }

    public RoNetWorkUtil params(Object params) {
        this.params = params;
        return this;
    }


    public RoNetWorkUtil conParams(String conParams) {
        this.conParams = conParams;
        return this;
    }

    private String url;
    private String conParams;

    public String getConParams() {
        return conParams;
    }

    public void setConParams(String conParams) {
        this.conParams = conParams;
    }

    private Object params;

    public Object getParams() {
        return params;
    }

    public String getUrl() {
        return url;

    }

    public void execute(BaseResponseCallback responseCallBack) {
        doGetUrl(getUrl(), getParams(), responseCallBack,true);
    }

    public void execute1(BaseResponseCallback responseCallBack) {
        doGetUrl(getUrl(), getParams(), responseCallBack,false);
    }

    private void doGetUrl(final String url, final Object params, final BaseResponseCallback responseCallBack, final boolean isObject) {
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                String result;
                if (isObject ) {
                    result = VivianHttpUtil.sendPost(url, params, "UTF-8");
                } else {
                    result = VivianHttpUtil.sendPost1(url, conParams, "UTF-8");
                }
                AppLogUtil.i("call" + result);
                subscriber.onNext(result);

            }
        }).observeOn(AndroidSchedulers.mainThread())//指定subscriber的回调发生在UI线程
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        AppLogUtil.i("onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLogUtil.i("发生了错误" + e.getMessage());

                    }

                    @Override
                    public void onNext(String s) {
                        AppLogUtil.i(s);

                        responseCallBack.onSuccess(s);
                        onCompleted();

                    }
                });
    }
}
