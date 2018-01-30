package com.wq.halfbeanapp.util.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wq.halfbeanapp.MyApplication;
import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.constants.AppConstants;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.net.response.ResponseBean;
import com.wq.halfbeanapp.util.AppLogUtil;
import com.wq.halfbeanapp.util.file.AppConfigFileImpl;
import com.wq.halfbeanapp.util.user.UserInfoUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vivianWQ on 2018/1/29
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HttpUtil {

    private static HttpUtil mHttpUtil;

    //请求连接的前缀
    private static final String BASE_URL = "";

    //连接超时时间
    private static final int REQUEST_TIMEOUT_TIME = 60 * 1000;

    //volley请求队列
    public static RequestQueue mRequestQueue;

    private HttpUtil() {
        //这里使用Application创建全局的请求队列
        mRequestQueue = Volley.newRequestQueue(MyApplication.getInstance());
    }

    public static HttpUtil getInstance() {
        if (mHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (mHttpUtil == null) {
                    mHttpUtil = new HttpUtil();
                }
            }
        }
        return mHttpUtil;
    }


    /**
     * http请求
     *
     * @param url          http地址（后缀）
     * @param param        参数
     * @param httpCallBack 回调
     */
    public <T> void request(String url, final Map<String, String> param, final HttpCallBack<T> httpCallBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppLogUtil.i("收到结果反馈" + response);
                        if (httpCallBack == null) {
                            return;
                        }

                        Type type = getTType(httpCallBack.getClass());


                        ResponseBean responseBean = JsonTools.getBean(response, ResponseBean.class);
                        if (responseBean != null) {
                            if (responseBean.getCode() != 200) {//失败
                                httpCallBack.onFail(responseBean.getMessage());
                            } else {//成功
                                //获取data对应的json字符串
                                String json = responseBean.getData().toString();
                                if (type == String.class) {//泛型是String，返回结果json字符串
                                    httpCallBack.onSuccess((T) json);
                                } else {//泛型是实体或者List<>


                                    Class<? super T> rawType = (Class<T>) type;
                                    Object bean = responseBean.getData(rawType);
                                    httpCallBack.onSuccess((T) bean);


                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpCallBack == null) {
                    return;
                }
                String msg = error.getMessage();
                httpCallBack.onFail(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //请求参数
                return param;
            }

            // 重写头信息，为了服务器授权
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<>();


                //如果已经登录，追加头信息
                if (AppConfigFileImpl.getBooleanParams(MyApplication.getInstance(), AppConstants.USER_LOGIN) == true) {
                    UserBean userInfo = UserInfoUtil.getUserInfo(MyApplication.getInstance());
                    headers.put("uid", userInfo.getUserId() + "");
                }

                return headers;
            }


        };
        //设置请求超时和重试
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        //加入到请求队列
        if (mRequestQueue != null)
            mRequestQueue.add(stringRequest.setTag(url));
    }


    /**
     * http请求
     *
     * @param url          http地址（后缀）
     * @param param        参数
     * @param httpCallBack 回调
     */
    public <T> void requestList(String url, final Map<String, String> param, final HttpListCallBack<T> httpCallBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL + url,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        AppLogUtil.i("收到结果反馈" + response);
                        if (httpCallBack == null) {
                            return;
                        }


                        Type myClass = httpCallBack.getClass().getGenericSuperclass();    //反射获取带泛型的class
                        if (myClass instanceof Class) {
                            throw new RuntimeException("Missing type parameter.");
                        }
                        Class<? super T> rawType = (Class<T>) ((ParameterizedType) httpCallBack.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

                        ResponseBean responseBean = JsonTools.getBean(response, ResponseBean.class);
                        if (responseBean != null) {
                            if (responseBean.getCode() != 200) {//失败
                                httpCallBack.onFail(responseBean.getMessage());
                            } else {//成功
                                //获取data对应的json字符串
                                List<T> bean = (List<T>) responseBean.getArrayData(rawType);
                                httpCallBack.onSuccess(bean);

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpCallBack == null) {
                    return;
                }
                String msg = error.getMessage();
                httpCallBack.onFail(msg);
            }

        }) {
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {

//                try {//jsonObject要和前面的类型一致,此处都是String
//                    String jsonObject = new String(new String(response.data, "UTF-8"));
//                    AppLogUtil.i("发生了错误1");
//                    return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                    AppLogUtil.i("发生了错误2"+e.getMessage());
//                    return Response.error(new ParseError(e));
//                }
                String str = null;
                try {
                    str = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));

            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //请求参数
                return param;
            }


            // 重写头信息，为了服务器授权
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<>();


                //如果已经登录，追加头信息
                if (AppConfigFileImpl.getBooleanParams(MyApplication.getInstance(), AppConstants.USER_LOGIN) == true) {
                    UserBean userInfo = UserInfoUtil.getUserInfo(MyApplication.getInstance());
                    headers.put("uid", userInfo.getUserId() + "");
                }
//                headers.put("Charset", "utf-8");
//                headers.put("Content-Type", "application/x-javascript");
//                headers.put("Accept-Encoding", "gzip,deflate");

                return headers;
            }


        };
        //设置请求超时和重试
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        //加入到请求队列
        if (mRequestQueue != null)
            mRequestQueue.add(stringRequest.setTag(url));
    }

    private Type getTType(Class<?> clazz) {
        Type mySuperClassType = clazz.getGenericSuperclass();
        Type[] types = ((ParameterizedType) mySuperClassType).getActualTypeArguments();
        if (types != null && types.length > 0) {
            //T
            return types[0];
        }
        return null;
    }


}
