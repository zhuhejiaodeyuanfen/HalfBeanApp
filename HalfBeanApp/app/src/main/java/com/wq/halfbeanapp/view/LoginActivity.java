package com.wq.halfbeanapp.view;

import android.os.Bundle;

import com.wq.halfbeanapp.R;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    @Override
    public void loadData() {

        launcher(LoginActivity.this, HomeActivity.class);
    }
}
