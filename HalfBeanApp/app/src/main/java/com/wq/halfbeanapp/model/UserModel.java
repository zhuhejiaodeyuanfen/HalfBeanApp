package com.wq.halfbeanapp.model;

import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.service.UserService;
import com.wq.halfbeanapp.util.retrofit.NetHttpApi;
import com.wq.halfbeanapp.util.retrofit.RxHelper;
import com.wq.halfbeanapp.util.retrofit.RxSubscribe;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class UserModel {
    private UserService userService;


    public UserModel() {
        userService = NetHttpApi.getInstance().getService(UserService.class);
    }


    public void getUserInfo(int uid, RxSubscribe<UserBean> subscribe) {
        userService.getUserInfo(uid)
                .compose(RxHelper.<UserBean>handleResult())
                .subscribe(subscribe);
    }


}
