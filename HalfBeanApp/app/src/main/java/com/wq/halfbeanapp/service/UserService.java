package com.wq.halfbeanapp.service;

import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.util.retrofit.BaseResponse;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:  首页话题service
 * 包括行为动作
 * 1.获取首页所有的评论集合--分页加载
 * 2.发表评论
 * 3.点赞
 * 4.踩一下
 * Version: 1.0
 */
public interface UserService {


    /**
     * 根据用户uid获取用户详情
     *
     * @param uid
     * @return
     */
    @POST("/wq/user/getUserInfo")
    Observable<BaseResponse<UserBean>> getUserInfo(@Query("uid") int uid);


}
