package com.wq.halfbeanapp.util;

/**
 * 对输入文件的验证
 */
public class AppInputCheckUtil {


    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }


    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return 是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    isChinese = true;
                }
            }
        }
        return isChinese;
    }

    /**
     * 密码格式判断
     * 需要同时包含数字和字母
     * @param str
     * @return
     */
    public static boolean isPassWord(String str){
        Boolean isNoLetter = false;
        String expr = ".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }
}
