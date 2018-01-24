package com.wq.halfbeanapp.widget.dialog;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by vivianWQ on 2017/5/2
 * Mail: wangqi_vivian@sina.com
 * desc: toast对话框
 * Version: 1.0
 */
public class ToastView {


    public static void showToast(String result, Context context) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resId, Context context) {
        showToast(context.getString(resId), context);
    }
}
