package com.wq.halfbeanapp.util.user;

import android.content.Context;
import android.text.TextUtils;

import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.util.file.AppConfigFileImpl;

/**
 * Created by vivianWQ on 2018/1/24
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class UserInfoUtil {

    public static void saveUserInfo(Context context, UserBean loginEntity) {
        if (null != loginEntity) {
            AppConfigFileImpl.saveParams(context, "userInfo", JsonTools.getJsonString(loginEntity));
        }
    }


    public static void clearUserInfo(Context context) {
        AppConfigFileImpl.saveParams(context, "userInfo", "");
    }

    /**
     * 获取用户信息
     *
     * @param context
     * @return
     */
    public static UserBean getUserInfo(Context context) {
        UserBean userBean = null;
        String userinfo = AppConfigFileImpl.getStringParams(context, "userInfo");
        if (!TextUtils.isEmpty(userinfo)) {
            userBean = JsonTools.getBean(userinfo, UserBean.class);
        }
        return userBean;
    }


}
