package com.wq.halfbeanapp.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.halfbeanapp.R;


/**
 * Created by vivianWQ on 2017/5/2
 * Mail: wangqi_vivian@sina.com
 * desc: toast对话框
 * Version: 1.0
 */
public class ToastDialog extends Dialog {


    private String mMessage;
    private ImageView ivToast;
    private TextView tvToast;
    private int mResId;

    private ToastDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_toast);
        tvToast = (TextView) findViewById(R.id.tvLine1);
        ivToast = (ImageView) findViewById(R.id.iv_toast);

    }

    public void setMessage(String msg, int mResId) {
        this.mMessage = msg;
        this.mResId = mResId;
    }

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(ToastDialog mDialog) {
        if (!TextUtils.isEmpty(mDialog.mMessage)) {
            mDialog.tvToast.setText(mDialog.mMessage);
        }
        mDialog.ivToast.setImageResource(mResId);


    }

    public static class Builder {

        private ToastDialog mDialog;

        public Builder(Context context) {
            mDialog = new ToastDialog(context);
        }


        /**
         * 设置对话框文本内容,如果调用了setView()方法，该项失效
         *
         * @param msg
         */
        public Builder setMessage(String msg, int mResId) {
            mDialog.mMessage = msg;
            mDialog.mResId = mResId;
            return this;
        }


        /**
         * 设置该对话框能否被Cancel掉，默认可以
         *
         * @param cancelable
         */
        public Builder setCancelable(boolean cancelable) {
            mDialog.setCancelable(cancelable);
            return this;
        }

        /**
         * 设置对话框被cancel对应的回调接口，cancel()方法被调用时才会回调该接口
         *
         * @param onCancelListener
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mDialog.setOnCancelListener(onCancelListener);
            return this;
        }

        /**
         * 设置对话框消失对应的回调接口，一切对话框消失都会回调该接口
         *
         * @param onDismissListener
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            mDialog.setOnDismissListener(onDismissListener);
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public ToastDialog create() {
            return mDialog;
        }
    }
}
