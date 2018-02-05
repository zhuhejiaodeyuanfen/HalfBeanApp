package com.wq.halfbeanapp.view;

import android.os.Bundle;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.presenter.UserPresenter;
import com.wq.halfbeanapp.view.iview.IUserView;

public class UserDetailActivity extends BaseActivity implements IUserView {
    private UserPresenter userPresenter;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
    }

    @Override
    public void initView() {
        initTitle("个人中心");
    }

    @Override
    public void initEventData() {
        userPresenter = new UserPresenter(UserDetailActivity.this, this);
        uid = getIntent().getIntExtra("uid", 0);

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void loadData() {
        userPresenter.getUserInfo(uid);

    }

    @Override
    public void showUser(UserBean userBean) {

    }
}