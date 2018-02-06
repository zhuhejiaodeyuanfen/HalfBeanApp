package com.wq.halfbeanapp.presenter;

import com.wq.halfbeanapp.bean.LiveBoardModel;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataListResponseCallback;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.view.iview.IHotTopicFragment;

import java.util.List;

/**
 * Created by vivianWQ on 2018/1/26
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HotLivePresenter {


    private IHotTopicFragment iHotTopicFragment;

    public HotLivePresenter(IHotTopicFragment iHotTopicFragment) {
        this.iHotTopicFragment = iHotTopicFragment;
    }

    public void getLivePage(boolean isRefresh) {

        RoNetWorkUtil
                .getInstance()
                .get(UrlConstants.getAllHotList)
                .params("")
                .execute(new DataListResponseCallback<LiveBoardModel>() {
                    @Override
                    public void onResponseSuccess(List<LiveBoardModel> response) {
                        if (response != null && response.size() > 0)
                            iHotTopicFragment.showAllHot(response);

                    }

                    @Override
                    public void onResponseFail(String errorString) {

                    }
                });
    }
}
