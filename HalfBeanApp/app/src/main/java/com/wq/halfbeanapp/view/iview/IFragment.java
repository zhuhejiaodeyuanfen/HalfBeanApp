package com.wq.halfbeanapp.view.iview;

/**
 * Created by vivianWQ on 2018/1/31
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public interface IFragment {

    void showToast(int strId);


    void showToast(String message);



    void showProgressDialog(int msg);

    void cancelProgressDialog();



}
