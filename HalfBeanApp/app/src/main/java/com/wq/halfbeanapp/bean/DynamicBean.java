package com.wq.halfbeanapp.bean;

/**
 * Created by vivianWQ on 2017/12/6
 * Mail: wangqi_vivian@sina.com
 * desc: 首页动态bean
 * Version: 1.0
 */
public class DynamicBean {
    private int id;//动态消息id
    private int type;//首页动态类型
    private int commentCount;//评论数量
    private int praiseCount;//赞的数量
    private int rePostCount;//转发数量
    private long createTime;//动态发布时间
    private String userName;//发布动态的作者姓名
    private String userIconPath;//发布动态的作者头像地址
    private int isUserFollow;//用户是否关注
}
