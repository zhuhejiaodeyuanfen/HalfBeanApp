package com.wq.halfbeanapp.util.retrofit;

import com.wq.halfbeanapp.MyApplication;
import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.util.user.UserInfoUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static com.wq.halfbeanapp.util.retrofit.RetrofitUtil.DEFAULT_TIMEOUT;

/**
 * 网络请求Api
 */
public class NetHttpApi {
    private Retrofit mRetrofit;
    //请求超时时间
    private static final int REQUEST_TIME = 10;
    private static NetHttpApi instance;

    private NetHttpApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(logging);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder1 = request.newBuilder();
                Request build = builder1.addHeader("Content-Type", " text/plain;charset=UTF-8")
                        .addHeader("accept", "application/json")
                        .addHeader("connection", "Keep-Alive")
                        .build();

                return chain.proceed(build);
            }
        });
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())

                .baseUrl(UrlConstants.baseIp)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    public void initHeader() {

        final UserBean userInfo = UserInfoUtil.getUserInfo(MyApplication.getInstance());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(logging);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder1 = request.newBuilder();
                Request build = builder1.addHeader("Content-Type", " text/plain;charset=UTF-8")
                        .addHeader("accept", "application/json")
                        .addHeader("connection", "Keep-Alive")
                        .addHeader("uid", userInfo.getUserId() + "")
                        .build();

                return chain.proceed(build);
            }
        });
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())

                .baseUrl(UrlConstants.baseIp)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static NetHttpApi getInstance() {
        if (instance == null) {
            synchronized (NetHttpApi.class) {
                if (instance == null) {
                    instance = new NetHttpApi();
                }
            }
        }
        return instance;
    }

    public <T> T getService(Class<T> service) {
        return mRetrofit.create(service);
    }
}
