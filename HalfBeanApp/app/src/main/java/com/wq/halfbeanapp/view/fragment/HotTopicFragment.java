package com.wq.halfbeanapp.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.HotTopicLiveAdapter;
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.bean.LiveBoardModel;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataListResponseCallback;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.view.topic.TopicCardActivity;

import java.util.List;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HotTopicFragment extends BaseFragment {
    private RecyclerView rvHotList;
    private HotTopicLiveAdapter hotTopicLiveAdapter;


    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_beantopic;
    }

    @Override
    public void initView() {
        rvHotList = (RecyclerView) mContentView.findViewById(R.id.rvHotList);

    }

    @Override
    public void initEventData() {
        hotTopicLiveAdapter = new HotTopicLiveAdapter(mContext);
        rvHotList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvHotList.setAdapter(hotTopicLiveAdapter);
        //点击进入cardView界面

        hotTopicLiveAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle args = new Bundle();
                args.putSerializable("data", hotTopicLiveAdapter.getItem(position));
                getBaseActivity().launcher(mContext, TopicCardActivity.class, args);

            }
        });

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
                .execute(new DataListResponseCallback<LiveBoardModel>() {
                    @Override
                    public void onResponseSuccess(List<LiveBoardModel> response) {
                        if (response != null && response.size() > 0)
                            hotTopicLiveAdapter.addData(response);

                    }

                    @Override
                    public void onResponseFail(String errorString) {

                    }
                });

    }
}
