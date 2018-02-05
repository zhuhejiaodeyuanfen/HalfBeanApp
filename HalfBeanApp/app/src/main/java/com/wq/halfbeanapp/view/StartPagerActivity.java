package com.wq.halfbeanapp.view;

import android.os.Bundle;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.constants.AppConstants;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataResponseCallback;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.util.file.AppConfigFileImpl;
import com.wq.halfbeanapp.util.retrofit.NetHttpApi;
import com.wq.halfbeanapp.util.user.UserInfoUtil;
import com.wq.halfbeanapp.view.home.LoginActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class StartPagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_pager);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEventData() {

    }

    @Override
    public void bindEvent() {


    }

    public void enterApp() {
        if (AppConfigFileImpl.getBooleanParams(StartPagerActivity.this, AppConstants.USER_LOGIN) == true) {
            //用户已登录,直接进入主页,需要更新用户信息
            if (UserInfoUtil.getUserInfo(StartPagerActivity.this) != null) {

                RoNetWorkUtil
                        .getInstance()
                        .get(UrlConstants.GET_USER_CONTENT)
                        .conParams("username="+UserInfoUtil.getUserInfo(StartPagerActivity.this).getUserName())
                        .execute1(new DataResponseCallback<UserBean>() {
                            @Override
                            public void onResponseSuccess(UserBean response) {
                                NetHttpApi.getInstance().initHeader();
                                UserInfoUtil.saveUserInfo(StartPagerActivity.this, response);
                                launcher(StartPagerActivity.this, HomeActivity.class);

                            }

                            @Override
                            public void onResponseFail(String errorString) {

                            }
                        });

            } else {
                launcher(StartPagerActivity.this, LoginActivity.class);
            }


        } else {
            launcher(StartPagerActivity.this, LoginActivity.class);
        }
    }

    @Override
    public void loadData() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                enterApp();
            }
        });

    }
}
