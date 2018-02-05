package com.wq.halfbeanapp.net.response;

import com.wq.halfbeanapp.util.AppLogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by apple on 2017/3/8.
 * 数据callBack 将泛型传递后直接拿到该实体类
 */

public abstract class DataResponseCallback<T> extends BaseResponseCallback {

    private Class<? super T> rawType;


    public DataResponseCallback() {
        Type myClass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myClass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        rawType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public void onSuccess(String response) {
        //根据code是不是0判断是0
        ResponseBean responseBean = JsonTools.getBean(response, ResponseBean.class);
        if (responseBean.isSuccess()) {
            onResponseSuccess((T) responseBean.getData(rawType));
        } else {
            AppLogUtil.i("失败了");
            onResponseFail(responseBean.getMessage());
        }
    }


    public abstract void onResponseSuccess(T response);


}
