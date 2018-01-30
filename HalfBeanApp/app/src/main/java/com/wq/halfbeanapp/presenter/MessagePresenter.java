package com.wq.halfbeanapp.presenter;

import android.content.Context;

import com.wq.halfbeanapp.model.MsgModel;
import com.wq.halfbeanapp.util.retrofit.RxSubscribe;
import com.wq.halfbeanapp.view.iview.IActivity;
import com.wq.halfbeanapp.view.iview.IMsgView;

import java.util.List;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class MessagePresenter<T extends IActivity> {
    private MsgModel msgModel;
    private Context mContext;
    private IMsgView mView;

    public MessagePresenter(Context mContext, T mView) {
        msgModel = new MsgModel();
        this.mContext = mContext;
        this.mView = (IMsgView) mView;
    }

    public void getUserMsgList(int msgCountId, int page) {
        msgModel.getMsgList(msgCountId, page, new RxSubscribe<List<com.wq.halfbeanapp.bean.MsgDetailModel>>(mContext, true) {
            @Override
            protected void _onNext(List<com.wq.halfbeanapp.bean.MsgDetailModel> msgModels) {
                mView.getMsgList(msgModels);


            }

            @Override
            protected void _onError(String message) {

            }
        });

    }
}
