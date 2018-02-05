package com.wq.halfbeanapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.view.BaseActivity;
import com.wq.halfbeanapp.view.iview.IDialogTwoView;
import com.wq.halfbeanapp.view.iview.IFragment;
import com.wq.halfbeanapp.widget.titlebar.TitleBar;


/**
 * 所有的fragment都必须
 */
public abstract class BaseFragment extends Fragment implements IFragment {

    public BaseActivity mContext;
    public View mContentView = null;


    /**
     * 初始化页面
     */
    public void init() {
        initView();
        initEventData();
        bindEvent();
        loadData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getBaseActivity();
//        EventBus.getDefault().register(this);
    }

    public TitleBar initTitle(String title) {
        TitleBar uiTitleBar = (TitleBar)mContentView.findViewById(R.id.titleBarView);
        uiTitleBar.setTitleText(title);
        return uiTitleBar;
    }


    public abstract void initEventData();

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

    public BaseActivity getBaseActivity() {
        return (BaseActivity) this.getActivity();
    }


    public void showToast(int strId) {
        getBaseActivity().showToast(strId);
    }


    public void showToast(String message) {
        getBaseActivity().showToast(message);
    }


    public void showProgressDialog(int msg) {
        getBaseActivity().showProgressDialog(msg);
    }

    public void cancelProgressDialog() {
        getBaseActivity().cancelProgressDialog();
    }

    /**
     * 设置布局文件
     *
     * @return 返回布局文件资源Id
     */
    public abstract int onSetLayoutId();


    /**
     * 初始化view
     */
    public abstract void initView();


    /**
     * 绑定事件
     */
    public abstract void bindEvent();

    /**
     * 拿数据
     */
    public abstract void loadData();


    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(onSetLayoutId(), container, false);
            init();
        }
        return mContentView;
    }

    /**
     * 打开一个普通的弹窗
     *
     * @param msg
     */
    public void dialog(String msg) {

    }

    public void dialog(String msg, String sureStr) {

    }

    public void dialog(String msg, String cancelStr, String sureStr) {

    }

    /**
     * 弹toast
     *
     * @param s
     */
    public void toast(String s) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 弹消息
     *
     * @param msg 消息
     * @param res 图片
     */
    public void toast(String msg, int res) {
    }

    /**
     * 显示进度框
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {

    }

    /**
     * 隐藏进度框
     */
    public void hideProgressDialog() {

    }
}
