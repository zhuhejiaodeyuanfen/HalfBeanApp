package com.wq.halfbeanapp.util.retrofit;

import com.wq.halfbeanapp.net.response.JsonTools;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class RxHelper {

    /**
     * 用来统一处理Http的resultCode,并将返回的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static <T> Observable.Transformer<BaseResponse<T>, T> handleResult() {
        return new Observable.Transformer<BaseResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResponse<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseResponse<T> result) {
                        if (result.getCode() == 200 && result.getData() != null) {
                            return createData(result.getData());
                        } else {
                            return Observable.error(new Exception(result.getMsg()));
                        }
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }


    public static  Observable.Transformer<String, BaseResponse1> handleResultResponse() {
        return new Observable.Transformer<String, BaseResponse1>() {
            @Override
            public Observable<BaseResponse1> call(Observable<String> tObservable) {
                return tObservable.flatMap(new Func1<String, Observable<BaseResponse1>>() {
                    @Override
                    public Observable<BaseResponse1> call(String result) {

                        return createData(result);

                    }
                })
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    /**
     * 将数据存入subscriber
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }

    private static Observable<BaseResponse1> createData(final String data) {
        return Observable.create(new Observable.OnSubscribe<BaseResponse1>() {
            @Override
            public void call(Subscriber<? super BaseResponse1> subscriber) {
                try {
                    subscriber.onNext(JsonTools.getBean(data, com.wq.halfbeanapp.util.retrofit.BaseResponse1.class));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }
}
