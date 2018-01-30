package com.wq.halfbeanapp.service;

import com.wq.halfbeanapp.bean.MsgDetailModel;
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

    @POST("/wq/msg/msgFrom/getMsgList")
    Observable<BaseResponse<List<MsgDetailModel>>> getMsgList(@Query("msgCountId") int msgCountId, @Query("page") int page);
}
