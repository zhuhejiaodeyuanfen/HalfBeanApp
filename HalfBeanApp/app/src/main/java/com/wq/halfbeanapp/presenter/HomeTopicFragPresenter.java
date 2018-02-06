package com.wq.halfbeanapp.presenter;

import android.content.Context;

import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.model.HomeTopicFragModel;
import com.wq.halfbeanapp.util.retrofit.RxSubscribe;
import com.wq.halfbeanapp.view.iview.IFragment;
import com.wq.halfbeanapp.view.iview.IHomeTopicFragView;

import java.util.List;

/**
 * Created by vivianWQ on 2018/2/6
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HomeTopicFragPresenter<T extends IFragment> {
    private Context mContext;
    private IHomeTopicFragView iHomeTopicFragView;

    public HomeTopicFragPresenter(Context mContext, T mView) {
        homeTopicFragModel = new HomeTopicFragModel();
        this.mContext = mContext;
        this.iHomeTopicFragView = (IHomeTopicFragView) mView;
    }

    private HomeTopicFragModel homeTopicFragModel;

    public void getHomeTopicList(int page) {
        homeTopicFragModel.getHomeTopicList(page, new RxSubscribe<List<HomeBoardDetailModel>>(mContext, false) {
            @Override
            protected void _onNext(List<HomeBoardDetailModel> commentBeans) {
                iHomeTopicFragView.showHomeTopicList(commentBeans);

            }

            @Override
            protected void _onError(String message) {

            }
        });

    }
}
