package com.wq.halfbeanapp.service;

import com.wq.halfbeanapp.bean.CommentBean;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.bean.UserMessageList;
import com.wq.halfbeanapp.util.retrofit.BaseResponse;

import java.util.List;

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
public interface HomeTopicService {

    /**
     * 根据帖子id 获取所有的评论列表
     *
     * @param postId
     * @param page
     * @return
     */
    @POST("/wq/comment/getComment")
    Observable<BaseResponse<List<CommentBean>>> getCommentList(@Query("postId") int postId, @Query("page") int page);


    /**
     * 获取用户fragment页面的消息列表
     *
     * @param page
     * @return
     */
    @POST("/wq/msg/getMsgList")
    Observable<BaseResponse<List<UserMessageList>>> getListUserMsg(@Query("page") int page);


    /**
     * 获取首页所有帖子列表
     *
     * @param page
     * @return
     */
    @POST("/wq/home/getHomeLive")
    Observable<BaseResponse<List<HomeBoardDetailModel>>> getHomeTopicList(@Query("page") int page);


}
