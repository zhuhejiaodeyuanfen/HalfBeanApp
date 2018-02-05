package com.wq.halfbeanapp.presenter;

import android.content.Context;

import com.wq.halfbeanapp.bean.CommentBean;
import com.wq.halfbeanapp.bean.SocketModel;
import com.wq.halfbeanapp.constants.SocketParams;
import com.wq.halfbeanapp.model.HomeTopicModel;
import com.wq.halfbeanapp.util.retrofit.RxSubscribe;
import com.wq.halfbeanapp.view.fragment.MessageFragment;
import com.wq.halfbeanapp.view.iview.IActivity;
import com.wq.halfbeanapp.view.iview.IHomeTopicView;

import java.util.List;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HomeTopicPresenter<T extends IActivity> {
    private HomeTopicModel homeTopicModel;
    private Context mContext;
    private IHomeTopicView mView;

    public HomeTopicPresenter(Context mContext, T mView) {
        homeTopicModel = new HomeTopicModel();
        this.mContext = mContext;
        this.mView = (IHomeTopicView) mView;
    }

    public void getPostCommentList(int postId, int page) {
        homeTopicModel.getCommentList(postId, page, new RxSubscribe<List<CommentBean>>(mContext, true) {
            @Override
            protected void _onNext(List<CommentBean> commentBeans) {
                mView.getTopicCommentList(commentBeans);

            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    /**
     * 用户发送消息
     *
     * @param msgDetailModel
     */
    public boolean sendMsg(com.wq.halfbeanapp.bean.MsgModel msgDetailModel) {
        SocketModel socketModel = new SocketModel();
        socketModel.setSocketType(SocketParams.SEND_MESSAGE);
        socketModel.setData(msgDetailModel);
        boolean result = MessageFragment.chatSocketPresenter.sendMsg(socketModel);
        return result;

    }
}
