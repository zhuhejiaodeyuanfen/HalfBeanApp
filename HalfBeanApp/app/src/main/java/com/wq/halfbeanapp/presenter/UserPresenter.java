package com.wq.halfbeanapp.presenter;

import android.content.Context;

import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.model.UserModel;
import com.wq.halfbeanapp.util.retrofit.RxSubscribe;
import com.wq.halfbeanapp.view.iview.IActivity;
import com.wq.halfbeanapp.view.iview.IUserView;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class UserPresenter<T extends IActivity> {
    private UserModel userModel;
    private Context mContext;
    private IUserView mView;

    public UserPresenter(Context mContext, T mView) {
        userModel = new UserModel();
        this.mContext = mContext;
        this.mView = (IUserView) mView;
    }

    public void getUserInfo(int uid) {
        userModel.getUserInfo(uid, new RxSubscribe<UserBean>(mContext, false) {
            @Override
            protected void _onNext(UserBean userBean) {
                mView.showUser(userBean);

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }


}
