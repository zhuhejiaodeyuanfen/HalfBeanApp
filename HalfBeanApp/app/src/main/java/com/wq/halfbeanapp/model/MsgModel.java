package com.wq.halfbeanapp.model;

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

    public void getMsgList(int msgCountId, int page, RxSubscribe<List<com.wq.halfbeanapp.bean.MsgDetailModel>> subscribe) {
        messageService.getMsgList(msgCountId, 0)
                .compose(RxHelper.<List<com.wq.halfbeanapp.bean.MsgDetailModel>>handleResult())
                .subscribe(subscribe);

    }
}
