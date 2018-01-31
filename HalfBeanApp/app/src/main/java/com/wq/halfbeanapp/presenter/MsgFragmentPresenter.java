package com.wq.halfbeanapp.presenter;

import android.content.Context;

import com.wq.halfbeanapp.bean.UserMessageList;
import com.wq.halfbeanapp.model.MsgModel;
import com.wq.halfbeanapp.util.retrofit.RxSubscribe;
import com.wq.halfbeanapp.view.iview.IFragment;
import com.wq.halfbeanapp.view.iview.IMsgFragmentView;

import java.util.List;

/**
 * Created by vivianWQ on 2018/1/31
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class MsgFragmentPresenter<T extends IFragment> {

    private Context mContext;
    private IMsgFragmentView iMsgFragmentView;

    public MsgFragmentPresenter(Context mContext, T mView) {
        msgModel = new MsgModel();
        this.mContext = mContext;
        this.iMsgFragmentView = (IMsgFragmentView) mView;
    }

    private MsgModel msgModel;

    public void getListUser(int page) {
        msgModel.getListUserMsg(page, new RxSubscribe<List<UserMessageList>>(mContext, true) {
            @Override
            protected void _onNext(List<com.wq.halfbeanapp.bean.UserMessageList> msgModels) {
                iMsgFragmentView.showUserMsgList(msgModels);


            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
