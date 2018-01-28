package com.wq.halfbeanapp.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by vivianWQ on 2018/1/28
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class AppUtil {

    public static final void hideInputMethod(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) (context).getSystemService(Context.INPUT_METHOD_SERVICE);
        //隐藏软键盘
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        //显示软键盘
//        imm.showSoftInputFromInputMethod(tv.getWindowToken(), 0);


    }

    public static final void shownputMethod(Context context, EditText editText) {



        InputMethodManager inputManager = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);


    }
}
