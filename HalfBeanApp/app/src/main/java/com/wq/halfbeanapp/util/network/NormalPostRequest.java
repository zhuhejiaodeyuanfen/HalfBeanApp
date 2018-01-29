package com.wq.halfbeanapp.util.network;

import com.alibaba.fastjson.JSONException;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.net.response.ResponseBean;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by vivianWQ on 2018/1/29
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class NormalPostRequest extends Request<ResponseBean> {
    private Map<String, String> mMap;
    private Response.Listener<ResponseBean> mListener;

    public NormalPostRequest(String url, Response.Listener<ResponseBean> listener, Response.ErrorListener errorListener, Map<String, String> map) {
        super(Request.Method.POST, url, errorListener);

        mListener = listener;
        mMap = map;
    }

    //mMap是已经按照前面的方式,设置了参数的实例
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    //此处因为response返回值需要json数据,和JsonObjectRequest类一样即可
    @Override
    protected Response<ResponseBean> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(JsonTools.getBean(jsonString, ResponseBean.class), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(ResponseBean response) {
        mListener.onResponse(response);
    }
}