package com.wq.halfbeanapp.presenter;

import android.content.Context;

import com.wq.halfbeanapp.bean.LivePhotoDetailModel;
import com.wq.halfbeanapp.model.HomeTopicModel;
import com.wq.halfbeanapp.util.retrofit.RxSubscribe;
import com.wq.halfbeanapp.view.iview.IActivity;
import com.wq.halfbeanapp.view.iview.IAddTopicView;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class UserInLivePresenter<T extends IActivity> {
    private HomeTopicModel homeTopicModel;
    private Context mContext;
    private IAddTopicView mView;

    public UserInLivePresenter(Context mContext, T mView) {
        homeTopicModel = new HomeTopicModel();
        this.mContext = mContext;
        this.mView = (IAddTopicView) mView;
    }


    public void userInLive(LivePhotoDetailModel livePhotoDetailModel) {
        homeTopicModel.userInALive(livePhotoDetailModel, new RxSubscribe<String>(mContext, true) {
            @Override
            protected void _onNext(String s) {
                mView.commitResult(s);

            }

            @Override
            protected void _onError(String message) {

            }
        });

    }


}
