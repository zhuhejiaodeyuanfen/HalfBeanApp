package com.wq.halfbeanapp.util.retrofit;

import com.wq.halfbeanapp.bean.MsgModel;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public interface ApiService {

//    /**
//     * 使用普通的retrofit方式获取数据
//     *
//     * @return
//     */
//    @GET("ezSQL/get_user.php")
//    Call<ResponseBean<List<MsgModel>>> getMsgList();


    /**
     * 使用rx+retrofit的方式获取数据
     */
//    @GET("ezSQL/get_user.php")
    Observable<BaseResponse<List<MsgModel>>> getMsgList();


//    @GET("api/cook/list")
//    Observable<TngouResponse<List<Cook>>> getCookList(@Query("page") int page, @Query("rows") int rows);

    @POST("/wq/msg/msgFrom/getMsgList")
    Observable<BaseResponse<List<MsgModel>>> getMsgList(@Query("msgCountId") int msgCountId, @Query("page") int page);

    @POST("/wq/msg/msgFrom/getMsgList")
    Observable<BaseResponse<List<MsgModel>>> getMsgList1(@Query("msgCountId") int msgCountId, @Query("page") int page);
}
