package com.wq.halfbeanapp.view.home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.constants.AppConstants;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataResponseCallback;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.util.AppStrUtil;
import com.wq.halfbeanapp.util.file.AppConfigFileImpl;
import com.wq.halfbeanapp.util.user.UserInfoUtil;
import com.wq.halfbeanapp.view.BaseActivity;
import com.wq.halfbeanapp.view.HomeActivity;
import com.wq.halfbeanapp.view.RegisterActivity;

public class LoginActivity extends BaseActivity {
    private EditText etUser, etPassWord;
    private TextView tvLogin, tvRegister;
    private String passWord, userName;
    private String loginStr;

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
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher(LoginActivity.this, RegisterActivity.class);
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
        loginStr = "userName=" + userName + "&&passWord=" + passWord;


        RoNetWorkUtil
                .getInstance()
                .get(UrlConstants.USER_LOGIN)
                .conParams(loginStr)
                .execute1(new DataResponseCallback<UserBean>() {
                    @Override
                    public void onResponseSuccess(UserBean response) {
                        UserInfoUtil.saveUserInfo(LoginActivity.this, response);
                        cancelProgressDialog();
                        showToast("登陆成功");
                        AppConfigFileImpl.saveParams(LoginActivity.this, AppConstants.USER_LOGIN, true);
                        launcher(LoginActivity.this, HomeActivity.class);


                    }

                    @Override
                    public void onResponseFail(String errorString) {

                        cancelProgressDialog();
                        showToast(errorString);

                    }
                });
    }
}
