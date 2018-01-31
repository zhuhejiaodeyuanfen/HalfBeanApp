package com.wq.halfbeanapp.model;

import com.wq.halfbeanapp.bean.UserMessageList;
import com.wq.halfbeanapp.service.MessageService;
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
public class MsgModel {
    private MessageService messageService;


    public MsgModel() {
        messageService = NetHttpApi.getInstance().getService(MessageService.class);
    }

    /***
     * 获取 具体用户对象的消息列表
     * @param msgCountId
     * @param page
     * @param subscribe
     */
    public void getMsgList(int msgCountId, int page, RxSubscribe<List<com.wq.halfbeanapp.bean.MsgDetailModel>> subscribe) {
        messageService.getMsgList(msgCountId, 0)
                .compose(RxHelper.<List<com.wq.halfbeanapp.bean.MsgDetailModel>>handleResult())
                .subscribe(subscribe);

    }

    /**
     * 获取用户fragment 列表 消息用户
     *
     * @param page
     * @param subscribe
     */
    public void getListUserMsg(int page, RxSubscribe<List<UserMessageList>> subscribe) {
        messageService.getListUserMsg(page)
                .compose(RxHelper.<List<UserMessageList>>handleResult())
                .subscribe(subscribe);
    }

}
