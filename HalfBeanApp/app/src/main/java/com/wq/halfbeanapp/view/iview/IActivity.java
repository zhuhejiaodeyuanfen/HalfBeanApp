package com.wq.halfbeanapp.view.iview;

/**
 * Created by wangqi on 2016/10/17.
 */

public interface IActivity {

    void showToast(int strId);


    void showToast(String message);

    boolean hasNetwork();

    boolean hasNetworkMsg();

    void showProgressDialog(int msg);

    void cancelProgressDialog();

    void showNetError();

    void showDialogToast(int res, String toast);

    void showProgressDialogCancel(String msg);

}
