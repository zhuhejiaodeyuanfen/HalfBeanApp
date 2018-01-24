package com.wq.halfbeanapp.view;

import android.os.Bundle;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.constants.AppConstants;
import com.wq.halfbeanapp.util.file.AppConfigFileImpl;

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
            launcher(StartPagerActivity.this, HomeActivity.class);
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
