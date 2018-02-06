package com.wq.halfbeanapp.model;

import com.wq.halfbeanapp.bean.CommentBean;
import com.wq.halfbeanapp.bean.LivePhotoDetailModel;
import com.wq.halfbeanapp.service.HomeTopicService;
import com.wq.halfbeanapp.util.retrofit.NetHttpApi;
import com.wq.halfbeanapp.util.retrofit.RxHelper;
import com.wq.halfbeanapp.util.retrofit.RxSubscribe;

import java.util.List;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HomeTopicModel {
    private HomeTopicService homeTopicService;


    public HomeTopicModel() {
        homeTopicService = NetHttpApi.getInstance().getService(HomeTopicService.class);
    }


    /**
     * 获取特定帖子的评论列表
     *
     * @param postId
     * @param page
     * @param subscribe
     */
    public void getCommentList(int postId, int page, RxSubscribe<List<CommentBean>> subscribe) {
        homeTopicService.getCommentList(postId, page)
                .compose(RxHelper.<List<com.wq.halfbeanapp.bean.CommentBean>>handleResult())
                .subscribe(subscribe);
    }

    /**
     * 用户参与一场live
     *
     * @param livePhotoDetailModel
     * @param subscribe
     */
    public void userInALive(LivePhotoDetailModel livePhotoDetailModel, RxSubscribe<String> subscribe) {
        homeTopicService.userAddLive(livePhotoDetailModel)
                .compose(RxHelper.<String>handleResult())
                .subscribe(subscribe);
    }


}
