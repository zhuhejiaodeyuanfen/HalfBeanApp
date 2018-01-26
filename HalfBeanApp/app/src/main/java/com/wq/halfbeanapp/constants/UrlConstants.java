package com.wq.halfbeanapp.constants;

/**
 * Created by vivianWQ on 2018/1/17
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class UrlConstants {

    public static final String baseIp = "http://192.168.10.65:8080";

    /**
     * 获取所有动态列表
     */
    public static final String getAllHotList = baseIp + "/wq/live/getAllLive";


    /**
     * 获取动态详情
     */
    public static final String QUERY_HOT_List = baseIp + "/wq/live/queryLive";

    /**
     * 获取首页新闻
     */
    public static final String GET_HOME_LIST = baseIp + "/wq/home/getHomeLive";

    /**
     * 获取评论列表
     */
    public static final String GET_COMMENT_LIST = baseIp + "/wq/comment/getComment";

    /**
     * 用户登陆链接
     */
    public static final String USER_LOGIN = baseIp + "/wq/user/login";

    /**
     * 添加一个新话题
     */
    public static final String ADD_HOME_TOPIC = baseIp + "/wq/home/addHotTopic";

    /**
     * 获取用户信息
     */
    public static final String GET_USER_CONTENT = baseIp + "/wq/user/getUser-content";


    /**
     * 更新用户头像
     */
    public static final String UPDATE_USER_ICON = baseIp + "/wq/user/update-icon";

}
