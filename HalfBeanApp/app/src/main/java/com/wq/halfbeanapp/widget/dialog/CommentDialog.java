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
import android.widget.EditText;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.util.AppUtil;

/**
 * Created by vivianWQ on 2017/11/21
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class CommentDialog extends Dialog {
    private View mView;
    private Context context;
    private EditText etContent;
    private IDialog iDialog;


    private CommentDialog(Context context) {
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
        View viewDialog = LayoutInflater.from(context).inflate(R.layout.layout_dialog_comment, null);
        etContent = (EditText) viewDialog.findViewById(R.id.etContent);
        setContentView(viewDialog, layoutParams);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);


        setCanceledOnTouchOutside(true);

    }


    public int getSelection()
    {
         return etContent.getSelectionStart();
    }

    public String getText()
    {
        return etContent.getText().toString();
    }
    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(final CommentDialog mDialog) {

        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();
        etContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtil.shownputMethod(context,etContent);


            }
        },200);



    }


    public interface IDialog {
        void selectPhotoList();
    }

    public static class Builder {

        private CommentDialog mDialog;


        public Builder(Context context) {
            mDialog = new CommentDialog(context);
        }


        public CommentDialog.Builder setIDialog(IDialog IDialog) {
            mDialog.setiDialog(IDialog);
            return this;
        }


        /**
         * 设置对话框被cancel对应的回调接口，cancel()方法被调用时才会回调该接口
         *
         * @param onCancelListener
         */
        public CommentDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mDialog.setOnCancelListener(onCancelListener);
            return this;
        }

        /**
         * 设置对话框消失对应的回调接口，一切对话框消失都会回调该接口
         *
         * @param onDismissListener
         */
        public CommentDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
            mDialog.setOnDismissListener(onDismissListener);
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public CommentDialog create() {
            return mDialog;
        }
    }

}
