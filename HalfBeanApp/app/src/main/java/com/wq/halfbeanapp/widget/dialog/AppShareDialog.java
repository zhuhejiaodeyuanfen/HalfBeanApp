package com.wq.halfbeanapp.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wq.halfbeanapp.R;

/**
 * Created by vivianWQ on 2017/11/21
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class AppShareDialog extends Dialog {
    private View mView;
    private Context context;
    private TextView tvSelectPhoto;
    private IDialog iDialog;


    private AppShareDialog(Context context) {
        super(context, R.style.ios_bottom_dialog);
        this.context = context;

    }

    public IDialog getiDialog() {
        return iDialog;
    }

    public void setiDialog(IDialog iDialog) {
        this.iDialog = iDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = this.getWindow().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        View viewDialog = LayoutInflater.from(context).inflate(R.layout.layout_dialog_select_icon, null);
        tvSelectPhoto = (TextView) viewDialog.findViewById(R.id.tvSelectPhoto);
        setContentView(viewDialog, layoutParams);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        tvSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDialog.selectPhotoList();
            }
        });

        setCanceledOnTouchOutside(true);
    }

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(final AppShareDialog mDialog) {


    }


    public interface IDialog {
        void selectPhotoList();
    }

    public static class Builder {

        private AppShareDialog mDialog;


        public Builder(Context context) {
            mDialog = new AppShareDialog(context);
        }


        public AppShareDialog.Builder setIDialog(IDialog IDialog) {
            mDialog.setiDialog(IDialog);
            return this;
        }


        /**
         * 设置对话框被cancel对应的回调接口，cancel()方法被调用时才会回调该接口
         *
         * @param onCancelListener
         */
        public AppShareDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mDialog.setOnCancelListener(onCancelListener);
            return this;
        }

        /**
         * 设置对话框消失对应的回调接口，一切对话框消失都会回调该接口
         *
         * @param onDismissListener
         */
        public AppShareDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
            mDialog.setOnDismissListener(onDismissListener);
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public AppShareDialog create() {
            return mDialog;
        }
    }

}
