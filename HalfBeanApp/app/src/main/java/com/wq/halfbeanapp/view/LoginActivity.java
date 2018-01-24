package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.util.AppStrUtil;

public class LoginActivity extends BaseActivity {
    private EditText etUser, etPassWord;
    private TextView tvLogin, tvRegister;
    private String passWord, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        etUser = (EditText) findViewById(R.id.etUser);
        etPassWord = (EditText) findViewById(R.id.etPassWord);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);

    }

    @Override
    public void initEventData() {

    }

    @Override
    public void bindEvent() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin(etUser, etPassWord);

            }
        });

    }

    @Override
    public void loadData() {

    }


    /**
     * 使用密码登录
     *
     * @param etUserName
     * @param etPassword
     */
    public void userLogin(EditText etUserName, final EditText etPassword) {
        if (!AppStrUtil.checkIsNull(etUserName, R.string.contacts_null)) return;
        if (!AppStrUtil.checkIsNull(etPassword, R.string.pwd_null)) return;
        if (AppStrUtil.checkIsContainChinese(etPassword, "密码不能包含中文")) return;
        passWord = etPassword.getText().toString();//这里不需要去前后的空格
        userName = etUserName.getText().toString();
        showProgressDialog(R.string.login_progress_msg);
    }
}
