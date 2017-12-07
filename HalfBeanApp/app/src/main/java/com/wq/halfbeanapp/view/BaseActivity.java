package com.wq.halfbeanapp.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.wq.halfbeanapp.view.iview.IActivity;
import com.wq.halfbeanapp.view.iview.IDialogTwoView;

public abstract class BaseActivity extends AppCompatActivity implements IActivity {


    public void showDialogToast(int res, String toast) {


    }

    @SuppressLint("NewApi")
    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    protected View mContentView;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mContentView = view;
        init();
    }

    /**
     * 初始化view
     */
    public abstract void initView();


    /**
     * 初始化事件数据,可以初始化presenter
     */
    public abstract void initEventData();


    /**
     * 绑定事件
     */
    public abstract void bindEvent();

    /**
     * 拿数据
     */
    public abstract void loadData();
    /**
     * 初始化页面
     */
    public void init() {
//        AppLogUtil.i("重复初始化了");
        initView();
        initEventData();
        bindEvent();
        loadData();
    }


    /**
     * 打开一个普通的弹窗
     * <p>
     * 有内容文本,有取消文本,回调
     *
     * @param msg
     * @param sureStr
     * @param iDialogView
     */
    public void showDialog(String msg, String cancelStr, String sureStr, final IDialogTwoView iDialogView) {

    }

    public void showDialog(String title, String content, String cancelStr, String sureStr, final IDialogTwoView iDialogView) {

    }

    public void showDialogUnableCancel(String title, String content, String cancelStr, String sureStr, final IDialogTwoView iDialogView) {

    }


    public void showDialogNoCancel(String title, String content, final IDialogTwoView iDialogView) {

    }

    public void showDialogNoCancelNoTitle(String content, final IDialogTwoView iDialogView) {

    }

    public void showDialogNoCancelNoTitle(String content, String sureStr, final IDialogTwoView iDialogView) {

    }

    public void showDialogUnableCancel(String title, String content, final IDialogTwoView iDialogView) {

    }

    public void showHandleDialogNoCancel(String title, String content, final IDialogTwoView iDialogView) {

    }


    /**
     * Toast提醒
     *
     * @param strId
     */
    public void showToast(int strId) {

    }

    public void showToast(String message) {


    }


    public void showNetError() {

        showDialog("温馨提示", "网络连接异常,请检查网络连接", "取消", "确定", new IDialogTwoView() {
            @Override
            public void cancel() {

            }

            @Override
            public void onSure() {

            }
        });

    }


    /**
     * 显示进度框
     *
     * @param msg 提示的文字
     */
    public void showProgressDialog(String msg) {

    }


    public void showProgressDialogCancel(String msg) {

    }

    public void showProgressDialog(int msg) {

    }

    public void cancelProgressDialog() {


    }


    /**
     * 网络检查
     *
     * @return
     */
    public boolean hasNetwork() {
        return true;
    }

    public boolean hasNetworkMsg() {
        return  true;
    }


    /**
     * 启动一个activity
     *
     * @param @param context
     * @param @param intent
     * @description
     */
    public static void launcher(Context context, Intent intent) {
        ActivityLauncher.launcher(context, intent);
    }


    public void launcher(Intent intent) {
        ActivityLauncher.launcher(this, intent);
    }


    /**
     * 启动一个activity
     *
     * @param context
     * @param targetActivity
     */
    public static void launcher(Context context, Class<?> targetActivity) {
        ActivityLauncher.activityLauncher(context, targetActivity);
    }

    public void launcher(Class<?> targetActivity) {
        ActivityLauncher.activityLauncher(this, targetActivity);
    }

    /**
     * 启动一个activity
     *
     * @param context
     * @param targetActivity
     * @param extras
     */
    public static void launcher(Context context, Class<? extends Activity> targetActivity, Bundle extras) {
        ActivityLauncher.launcherExtras(context, targetActivity, extras);
    }

    public void launcher(Class<? extends Activity> targetActivity, Bundle extras) {
        ActivityLauncher.launcherExtras(this, targetActivity, extras);
    }


    /**
     * 启动一个activity
     *
     * @param requestCode
     * @param context
     * @param targetActivity
     */
    public static void launcherResult(int requestCode, Context context, Class<? extends Activity> targetActivity) {
        ActivityLauncher.launcherResult(requestCode, context, targetActivity, null);
    }


    /**
     * 启动一个activity
     *
     * @param requestCode
     * @param context
     * @param targetActivity
     * @param extras
     */
    public static void launcherResult(int requestCode, Context context, Class<? extends Activity> targetActivity, Bundle extras) {
        ActivityLauncher.launcherResult(requestCode, context, targetActivity, extras);
    }

    public static void launcherResult(Context context, int requestCode, Intent intent) {
        ActivityLauncher.launcherResult(context, requestCode, intent);
    }


    /**
     * 关闭activity 使用默认动画
     *
     * @param context
     */
    public static void finishActivity(Context context) {
        ActivityLauncher.finishActivity(context);
    }

    public void finishActivity() {
        ActivityLauncher.finishActivity(this);
    }


    /**
     * 关闭activity 使用默认动画
     *
     * @param context
     * @param resultCode
     * @param data
     */
    public static void finishActivityResult(Context context, int resultCode, Intent data) {
        ActivityLauncher.finishActivityResult(context, resultCode, data);
    }


    /**
     * 返回键监听，默认关闭activity，如果要做其他处理，可以重写onKeyBack()方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean back = onKeyBack(keyCode, event);
            if (!back) {
                finishActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击返回键时，需要拦截返回，重写这个方法
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyBack(int keyCode, KeyEvent event) {
        return false;
    }
}