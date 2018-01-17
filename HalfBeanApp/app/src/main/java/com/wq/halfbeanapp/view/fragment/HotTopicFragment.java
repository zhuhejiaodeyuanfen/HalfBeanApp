package com.wq.halfbeanapp.view.fragment;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.LiveBoardModel;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.ResponseCallBack;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HotTopicFragment extends BaseFragment {
    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_beantopic;
    }

    @Override
    public void initView() {

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void loadData() {

        RoNetWorkUtil
                .getInstance()
                .get(UrlConstants.getAllHotList)
                .params("")
                .execute(new ResponseCallBack<LiveBoardModel>() {
                    @Override
                    public void onResponseSuccess(LiveBoardModel response) {

                    }
                });

    }
}
