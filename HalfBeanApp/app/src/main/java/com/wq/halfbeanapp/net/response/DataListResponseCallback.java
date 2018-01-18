package com.wq.halfbeanapp.net.response;

import com.wq.halfbeanapp.util.AppLogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by apple on 2017/3/8.
 * 数组的responseCallBack
 */

public abstract class DataListResponseCallback<T> extends BaseResponseCallback {

    private Class<? super T> rawType;

    public DataListResponseCallback() {
        super();
        Type myClass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myClass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        rawType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }


    @Override
    public void onSuccess(String response) {
        ResponseBean responseBean = JsonTools.getBean(response, ResponseBean.class);
        if (responseBean.isSuccess()) {
            onResponseSuccess((List<T>) responseBean.getArrayData(rawType));
        } else {
            AppLogUtil.i("失败了");
            onResponseFail(responseBean.getMessage());
        }
    }


    public abstract void onResponseSuccess(List<T> response);


}
