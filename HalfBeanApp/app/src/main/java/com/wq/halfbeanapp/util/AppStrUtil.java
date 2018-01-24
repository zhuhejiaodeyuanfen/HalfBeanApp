/*
 * Copyright (C) 2013 www.418log.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wq.halfbeanapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.wq.halfbeanapp.widget.dialog.ToastView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

// TODO: Auto-generated Javadoc

/**
 * 描述：字符串处理类.
 *
 * @author zhaoqp
 * @version v1.0
 */
public final class AppStrUtil {


    public static String stringFilterPass(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        // 只允许字母和数字
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();


    }

    /**
     * 描述：是否包含中文.
     *
     * @param @param  textView
     * @param @param  message 提示信息字符串
     * @param @return 是否包含中文:是为true，否则false
     * @return boolean
     * @description
     * @author jiaBF
     */
    public static boolean checkIsContainChinese(TextView textView, String message) {
//        if (AppInputCheckUtil.isContainChinese(textView.getText().toString().trim())) {
//            ToastView.showToast(message, textView.getContext());
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }

    /**
     * @param @param  context
     * @param @param  input 输入内容
     * @param @param  message 提示信息字符串id
     * @param @return 是否非空
     * @return boolean
     * @description 非空验证，为空 return false 进行消息提示
     * @author jiaBF
     */
    public static boolean checkIsNull(TextView textView, int message) {
        if (!TextUtils.isEmpty(textView.getText().toString().trim())) {
            return true;
        } else {
            ToastView.showToast(message, textView.getContext());
            return false;
        }
    }

    public static List<String> splitLength400(String content) {
        List<String> listStr = new ArrayList<>();
        //字符串总长度
        int len = content.length();
        //总行数
        int lineNum = len % 400 == 0 ? len / 400 : len / 400 + 1;
        //临时存放字符串变量
        String subStr = "";
        for (int i = 1; i <= lineNum; i++) {
            if (i < lineNum) {
                subStr = content.substring((i - 1) * 400, i * 400);
            } else {
                subStr = content.substring((i - 1) * 400, len);
            }
            //添加分割字符串
            listStr.add(subStr);
        }
        //遍历LIST查看测试结果
        for (String string : listStr) {
            System.out.println(string);
        }

        return listStr;
    }

    /**
     * @param @param  context
     * @param @param  input 输入内容
     * @param @param  message 提示信息字符串
     * @param @return 是否非空
     * @return boolean
     * @description 非空验证，为空 return false 进行消息提示
     * @author jiaBF
     */
    public static boolean checkIsNull(TextView textView, String message) {
        if (!TextUtils.isEmpty(textView.getText().toString().trim())) {
            return true;
        } else {
            ToastView.showToast(message, textView.getContext());
            return false;
        }
    }


    public static int getStrLength(String txtString) {
        return txtString.length();
    }


    /**
     * @param @param  textView
     * @param @return
     * @return String
     * @description 获取输入框的内容
     * @author jiaBF
     */
    public static String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    /**
     * @param @param  context
     * @param @param  strid
     * @param @return
     * @return String
     * @description 根据stringId获取string字符串
     * @author jiaBF
     */
    public static String getStrById(Context context, int strid) {
        return context.getResources().getString(strid);
    }


    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }


    /**
     * 对TextView的非空验证
     *
     * @param textView
     * @return
     */
    public static boolean isEmpty(TextView textView) {
        return (textView == null || TextUtils.isEmpty(textView.getText()
                .toString().trim()));
    }

    /**
     * 对EditText的非空验证
     *
     * @param editText
     * @return
     */
    public static boolean isEmpty(EditText editText) {
        return (editText == null || TextUtils.isEmpty(editText.getText()
                .toString().trim()));
    }

    /**
     * 获取TextView内容
     *
     * @param textView
     * @return
     */
    public static String getString(TextView textView) {
        return textView == null ? null : textView.getText().toString().trim();
    }

    /**
     * 获取EditText内容
     *
     * @param editText
     * @return
     */
    public static String getString(EditText editText) {
        return editText == null ? null : editText.getText().toString();
    }


    /**
     * 解码
     *
     * @param str         解码数据
     * @param charsetName 编码格式
     * @return
     */
    public static String URLDecoder(String str, String charsetName) {
        try {
            return URLDecoder.decode(str, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 防止字符串为空
     *
     * @param str
     * @param textView
     */
    public static void checkIsNull(String str, TextView textView) {
        if (str != null) {
            textView.setText(str);
        }
    }


    /**
     * textView设置纯数字有问题
     *
     * @param str
     * @param textView
     */
    public static void checkIsNull(int str, TextView textView) {
        textView.setText(str + "");
    }


    /***
     1. 4位数及以下显示数字
     2. 5位数显示xx.xK
     3. 6位数显示xx.xW
     4. 7位数及以上显示99+W*/
    @SuppressLint("DefaultLocale")
    public static String numFormat(String classTime) {
        String num = "0";

        if (classTime != null && !classTime.equals("")) {
            double s = Double.parseDouble(classTime);

            num = classTime;

            if (s >= 1000000) {
                num = "99+W";
            } else if (s >= 100000) {
                num = String.format("%.1f" + "K", s / 1000);
            } else if (s >= 100000) {
                num = String.format("%.1f" + "W", s / 10000);
            }
        }
        return num;
    }

}
