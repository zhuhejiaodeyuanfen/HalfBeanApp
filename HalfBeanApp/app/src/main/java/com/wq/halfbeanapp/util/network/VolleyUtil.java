package com.wq.halfbeanapp.util.network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.net.response.ResponseBean;
import com.wq.halfbeanapp.net.response.ResponseCallBack;
import com.wq.halfbeanapp.util.AppLogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by vivianWQ on 2018/1/29
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class VolleyUtil<T> {

    private Class<? super T> rawType;

    public <T> T getData(Class<T> cla, String data) {
        if (data != null && !TextUtils.isEmpty(data)) {
            return JsonTools.getBean(data, cla);
        }
        return null;
    }

    public VolleyUtil() {
        Type myClass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myClass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        rawType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    public void postRequest(Context context, String url, final HashMap<String, String> hashMap, final ResponseCallBack<T> responseCallBack) {


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Request<ResponseBean> request = new NormalPostRequest(url,
                new Response.Listener<ResponseBean>() {
                    @Override
                    public void onResponse(ResponseBean response) {
                        AppLogUtil.i("response -> " + response.toString());
                        responseCallBack.onResponseSuccess((T) response.getData(rawType));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLogUtil.i(error.getMessage(), error);
                responseCallBack.onError(error.getMessage());
            }
        }, hashMap);

        requestQueue.add(request);
    }
}
