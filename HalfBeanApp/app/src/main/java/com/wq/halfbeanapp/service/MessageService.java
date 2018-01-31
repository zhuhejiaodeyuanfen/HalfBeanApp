package com.wq.halfbeanapp.service;

import com.wq.halfbeanapp.bean.MsgDetailModel;
import com.wq.halfbeanapp.bean.UserMessageList;
import com.wq.halfbeanapp.util.retrofit.BaseResponse;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc: 消息Service
 * Version: 1.0
 */
public interface MessageService {

    /**
     * 根据msgCountId获取消息历史列表
     *
     * @param msgCountId
     * @param page
     * @return
     */
    @POST("/wq/msg/msgFrom/getMsgList")
    Observable<BaseResponse<List<MsgDetailModel>>> getMsgList(@Query("msgCountId") int msgCountId, @Query("page") int page);


    /**
     * 获取用户fragment页面的消息列表
     *
     * @param page
     * @return
     */
    @POST("/wq/msg/getMsgList")
    Observable<BaseResponse<List<UserMessageList>>> getListUserMsg(@Query("page") int page);


}
